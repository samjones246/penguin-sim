import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PenguinPanel extends JPanel {
    private int bg = 0;
    //public static final int MAIN = 0, FISHING = 1, SHOP = 2, GAME = 3;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bgImage = null;
        try {
            if(bg==0){
                bgImage = ImageIO.read(new File("resources/images/bg.png"));
            }else if(bg==1){
                bgImage = ImageIO.read(new File("resources/images/fishing_bg.png"));
            }else if(bg==2){
                bgImage = ImageIO.read(new File("resources/images/shop_bg.png"));
            }else if(bg==3){
                bgImage = ImageIO.read(new File("resources/images/game_bg.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(bgImage, 0, 0, null);
    }

    public PenguinPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Changes the background image of this panel
     * @param bg One of the static constants of this class; MAIN, SHOP, FISHING or GAME
     */
    public void setBg(int bg){
        this.bg = bg;
        repaint();
    }
}
