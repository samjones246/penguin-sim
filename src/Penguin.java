import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Class to model the Player's Penguin.
 * Tracks stats and handles stat decay. Fires action events for stat updates.
 */
public class Penguin extends JLabel{
    private int food = 100, health = 100, happiness = 100;
    private boolean healthy = true, awake = true, gone = false;
    private int max = 100;
    private String name;
    private Timer t;
    private TimerTask tickTask;

    /**
     * Initialises the penguin, setting up the timer.
     */
    Penguin(){
        super(MainWindow.createImageIcon("images/penguin_256.png",null));
        name = "Penguin";
        t = new Timer();
        tickTask = new TimerTask() {
            int playTimer = 0;
            int sleepTimer = 0;
            @Override
            public void run() {
                if(food>0) {
                    depleteFood(5);
                    if(healthy) {
                        heal(3);
                    }
                }else{
                    depleteHealth(5);
                    depleteHappiness(15);
                }
                if(playTimer == 8){
                    playTimer = 0;
                    depleteHappiness(15);
                }
                if(!healthy){
                    depleteHappiness(5);
                    depleteFood(5);
                    depleteHealth(5);
                }
                Random r = new Random();
                r.setSeed(System.currentTimeMillis());
                if(r.nextInt(100)+1<=5){
                    sicken();
                }
                if(sleepTimer == 24){
                    sleepTimer = 0;
                    toggleSleep();
                }
                playTimer++;
                sleepTimer++;
            }
        };
    }

    /**
        Toggles the sleep status of the penguin.
     */
    private void toggleSleep() {
        awake = !awake;
    }

    /**
     * Getter for current food level.
     * @return Current food level.
     */
    public int getFood() {
        return food;
    }

    /**
     * Getter for current health level.
     * @return Current health level.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for current happiness level.
     * @return Current happiness level.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Getter for the penguin's current state of health.
     * @return True if healthy, false if ill.
     */
    public boolean isHealthy() {
        return healthy;
    }

    /**
     * Getter for the current sleep status of the penguin.
     * @return True if awake, false if asleep
     */
    public boolean isAwake() {
        return awake;
    }

    /**
     * Increases the penguins food level by a given amount.
     * @param amount Amount to fill food by
     */
    public void feed(int amount){
        food+=amount;
        if(food>max){
            food = max;
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Heal the penguin by a given amount.
     * @param amount Amount to fill health by.
     */
    public void heal(int amount){
        health+=amount;
        if(health>max){
            health=max;
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Increase penguin happiness by a certain amount.
     * @param amount Amount to increase happiness by.
     */
    public void happy(int amount){
        happiness+=amount;
        if(happiness>max){
            happiness=max;
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Awaken the penguin if it is sleeping.
     */
    public void awaken(){
        awake = true;
        notifyUpdate("UPDATE");
    }

    /**
     * Cures the penguin of illness.
     */
    public void cure(){
        healthy = true;
        notifyUpdate("UPDATE");
    }

    /**
     * Deplete penguin food level by a given amount.
     * @param amount Amount to decrease food by.
     */
    public void depleteFood(int amount){
        food-=amount;
        if(food<0){
            food = 0;
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Deplete penguin health level by a given amount.
     * @param amount Amount to decrease health by.
     */
    public void depleteHealth(int amount){
        health -= amount;
        if(health<=0){
            health=0;
            gone();
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Deplete penguin happiness level by a given amount.
     * @param amount Amount to decrease happiness by.
     */
    public void depleteHappiness(int amount){
        happiness -= amount;
        if(happiness<0){
            happiness=0;
        }
        notifyUpdate("UPDATE");
    }

    /**
     * Gives the penguin a new name.
     * @param name The name to give the penguin.
     */
    public void setName(String name){
        this.name = name;
        resume();
    }

    /**
     * Stops timer and notifies listeners that penguin has left.
     */
    private void gone(){
        tickTask.cancel();
        setVisible(false);
        notifyUpdate("GONE");
    }

    /**
     * Causes penguin to become ill.
     * Illness depletes health, food and happiness every tick until cured.
     */
    public void sicken(){
        healthy = false;
        notifyUpdate("UPDATE");
    }

    /**
     * Stops the timer.
     */
    public void pause(){
        t.cancel();
    }

    /**
     * Resumes the timer so long as the penguin has not left.
     */
    public void resume(){
        t.cancel();
        if(!gone){
            t = new Timer();
            t.scheduleAtFixedRate(tickTask, 5000, 5000);
        }
    }

    /**
     * Getter for the penguin's name
     * @return Name of this adorable penguin
     */
    public String getName(){
        return name;
    }

    /**
     * Fires an action event to listeners with a given command string.
     * Called whenever there is an update to the penguin's status.
     * @param command The command string to attach to the event.
     */
    private void notifyUpdate(String command){
        processEvent(new ActionEvent(this, 0, command));
    }

    /**
     * Add an ActionListener to the listener list.
     * @param l ActionListener to add.
     */
    public void addActionListener(ActionListener l){
        listenerList.add(ActionListener.class, l);
    }

    /**
     * Processes ActionEvents fired by the notifyUpdate method, passing them to registered listeners.
     * @param e ActionEvent to process
     */
    private void processEvent(ActionEvent e){
        for(ActionListener l : listenerList.getListeners(ActionListener.class)){
            l.actionPerformed(e);
        }
    }
}
