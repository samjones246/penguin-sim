import javafx.scene.control.ContextMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainWindow extends JFrame{
    Penguin penguin;
    JLabel health;
    JLabel food;
    JLabel happiness;

    MainWindow(String title){
        setTitle(title);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        JPanel penguinPanel = new PenguinPanel(new BorderLayout());
        penguinPanel.setPreferredSize(new Dimension(512,512));
        mainPanel.add(penguinPanel);
        Font statFont = new Font("Century Gothic",Font.PLAIN,36);
        Font buttonFont = new Font("Courier New",Font.PLAIN,24);

        String name = "Three";
        penguin = new Penguin(name);
        penguinPanel.add(penguin,BorderLayout.CENTER);
        health = new JLabel("100",createImageIcon("images/heart_32.png",null),SwingConstants.RIGHT);
        health.setFont(statFont);
        health.setForeground(Color.GREEN);
        food = new JLabel("100",createImageIcon("images/fish_32.png",null),SwingConstants.RIGHT);
        food.setFont(statFont);
        food.setForeground(Color.GREEN);
        happiness = new JLabel("100",createImageIcon("images/smile_32.png",null),SwingConstants.RIGHT);
        happiness.setFont(statFont);
        happiness.setForeground(Color.GREEN);
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS));
        statsPanel.setOpaque(false);
        statsPanel.add(health);
        statsPanel.add(Box.createHorizontalGlue());
        statsPanel.add(food);
        statsPanel.add(Box.createHorizontalGlue());
        statsPanel.add(happiness);
        penguinPanel.add(statsPanel, BorderLayout.NORTH);
        penguin.addActionListener(e -> {
            if(Objects.equals(e.getActionCommand(), "UPDATE")) {
                updateStats();
            }else if(Objects.equals(e.getActionCommand(), "GONE")){
                updateStats();
                gameOver();
            }
        });

        JPanel functionsPanel = new JPanel(new GridLayout());
        JButton feed = new JButton("FEED");
        feed.addActionListener(e -> penguin.feed(10));
        feed.setFont(buttonFont);
        JButton play = new JButton("PLAY");
        play.addActionListener(e -> penguin.happy(25));
        play.setFont(buttonFont);
        JButton cure = new JButton("CURE");
        cure.addActionListener(e -> {
            penguin.cure();
            penguin.heal(50);
        });
        cure.setFont(buttonFont);
        functionsPanel.add(feed);
        functionsPanel.add(play);
        functionsPanel.add(cure);
        mainPanel.add(functionsPanel,BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this,"Your penguin is tired of being neglected and has left. Adopt another?", "Game Over", JOptionPane.QUESTION_MESSAGE);
    }

    private void updateStats(){
        happiness.setText(""+penguin.getHappiness());
        if(penguin.getHappiness()==0){
            happiness.setForeground(Color.red);
        }else if(penguin.getHappiness()<25){
            happiness.setForeground(Color.orange);
        }else if(penguin.getHappiness()<50){
            happiness.setForeground(Color.yellow);
        }else{
            happiness.setForeground(Color.green);
        }
        health.setText(""+penguin.getHealth());
        if(penguin.getHealth()==0){
            health.setForeground(Color.red);
        }else if(penguin.getHealth()<25){
            health.setForeground(Color.orange);
        }else if(penguin.getHealth()<50){
            health.setForeground(Color.yellow);
        }else{
            health.setForeground(Color.green);
        }
        food.setText(""+penguin.getFood());
        if(penguin.getFood()==0){
            food.setForeground(Color.red);
        }else if(penguin.getFood()<25){
            food.setForeground(Color.orange);
        }else if(penguin.getFood()<50){
            food.setForeground(Color.yellow);
        }else{
            food.setForeground(Color.green);
        }
        if(penguin.isHealthy()){
            health.setIcon(createImageIcon("images/heart_32.png", null));
        }else{
            health.setIcon(createImageIcon("images/heart_sick_32.png", null));
        }
    }
    public static ImageIcon createImageIcon(String path,
                                      String description) {
        java.net.URL imgURL = MainWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        new MainWindow("Penguin");
    }
}
