import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainWindow extends JFrame{
    private Penguin penguin;
    private JLabel health;
    private JLabel food;
    private JLabel happiness;
    private JLabel coinsLabel;
    private JLabel fishCountLabel, medicineCountLabel, nameTag;
    private int fishCount = 999, medicineCount = 999, coins = 0;
    private JButton feed, cure, play;

    MainWindow(String title){
        setTitle(title);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        mainPanel.add(initPenguin());
        mainPanel.add(initFunctions(),BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        start();
    }

    private void start() {
        String name = JOptionPane.showInputDialog("Enter a name for your newly adopted penguin: ");
        if(name==null){
            System.exit(0);
        }else if(name.equals("")){
            name="Penguin";
        }
        penguin.setName(name);
        updateStats();
    }

    public JPanel initStats(){
        Font statFont = new Font("Century Gothic",Font.PLAIN,36);
        health = new JLabel("100",createImageIcon("images/heart_32.png",null),SwingConstants.RIGHT);
        health.setFont(statFont);
        health.setForeground(Color.GREEN);
        food = new JLabel("100",createImageIcon("images/fish_32.png",null),SwingConstants.RIGHT);
        food.setFont(statFont);
        food.setForeground(Color.GREEN);
        happiness = new JLabel("100",createImageIcon("images/smile_32.png",null),SwingConstants.RIGHT);
        happiness.setFont(statFont);
        happiness.setForeground(Color.GREEN);
        coinsLabel = new JLabel("0", createImageIcon("images/coin_32.png", null), SwingConstants.RIGHT);
        coinsLabel.setFont(statFont);
        JPanel topPanel = new JPanel();
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row1.setOpaque(false);
        row2.setOpaque(false);
        topPanel.setOpaque(false);
        row1.add(health);
        row1.add(Box.createHorizontalGlue());
        row1.add(food);
        row1.add(Box.createHorizontalGlue());
        row1.add(happiness);
        row2.add(coinsLabel);
        row2.add(Box.createHorizontalGlue());
        topPanel.add(row1);
        topPanel.add(row2);
        return topPanel;
    }
    public JPanel initFunctions(){
        Font buttonFont = new Font("Courier New",Font.PLAIN,24);
        JPanel functionsPanel = new JPanel(new GridLayout(2,3));
        feed = new JButton("FEED");
        feed.setFont(buttonFont);
        play = new JButton("PLAY");
        play.setFont(buttonFont);
        cure = new JButton("CURE");
        cure.setFont(buttonFont);
        nameTag = new JLabel("Name: ");
        fishCountLabel = new JLabel("Fish Available: ");
        medicineCountLabel = new JLabel("Medicine Available: ");
        functionsPanel.add(nameTag);
        functionsPanel.add(fishCountLabel);
        functionsPanel.add(medicineCountLabel);
        functionsPanel.add(feed);
        functionsPanel.add(play);
        functionsPanel.add(cure);
        cure.addActionListener(e -> {
            medicineCount--;
            penguin.cure();
            penguin.heal(50);
        });
        play.addActionListener(e -> {
            penguin.happy(25);

        });
        feed.addActionListener(e -> {
            fishCount--;
            penguin.feed(10);
        });
        return functionsPanel;
    }
    public JPanel initPenguin(){
        JPanel penguinPanel = new PenguinPanel(new BorderLayout());
        penguin = new Penguin();
        penguinPanel.setPreferredSize(new Dimension(512,512));
        penguinPanel.add(initStats(), BorderLayout.NORTH);
        penguinPanel.add(penguin,BorderLayout.CENTER);
        penguin.addActionListener(e -> {
            if(Objects.equals(e.getActionCommand(), "UPDATE")) {
                updateStats();
            }else if(Objects.equals(e.getActionCommand(), "GONE")){
                updateStats();
                gameOver();
            }else if(Objects.equals(e.getActionCommand(), "PAYOUT")){
                getCash();
            }
        });
        return penguinPanel;
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
        nameTag.setText("Name: "+penguin.getName());
        fishCountLabel.setText("Fish: " + fishCount);
        medicineCountLabel.setText("Medicine: " + medicineCount);
        coinsLabel.setText(""+coins);
        if(penguin.getFood()<100&&fishCount>0){
            feed.setEnabled(true);
        }else{
            feed.setEnabled(false);
        }
        if((penguin.getHealth()<100||!penguin.isHealthy())&&medicineCount>0){
            cure.setEnabled(true);
        }else{
            cure.setEnabled(false);
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

    public void getCash(){
        coins+=penguin.getHappiness();
        updateStats();
    }

    public static void main(String[] args) {
        new MainWindow("Penguin");
    }
}
