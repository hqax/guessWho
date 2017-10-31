

import java.awt.*;
import javax.swing.*;

/**
 * @author Spencer Bryant
 */
public class GuessWho {
static final int START_TIME = 99;
static int systemStartTime;
static String startInfo;
static final boolean PARTIAL_INFO = false;
static double startTime;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Spencer's Guess Who Game");

        //Set up game window
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        gameFrame.setSize(width, height);

        try{
        //adds game objects to game window
        Background background = new Background(gameFrame.getWidth(), gameFrame.getHeight());
        BorderLayout g = new BorderLayout();
        background.setLayout(g);
        background.init();
        gameFrame.add(background);
        gameFrame.setVisible(true);

        //set start time for logging
        systemStartTime = (int)(System.currentTimeMillis() / 1000);

        //exit flag
        boolean b = false;

        //main game loop
        do{
            pause(40);
            background.repaint();

            if (!b){
                //rules dialog
                JOptionPane.showMessageDialog (null,
                "Guess the character the computer chose by asking questions about the character's features using the bar at the left and the \"ASK\" button."
                + "\nEach time you ask, 10 points will be deducted from your score and if you guess the correct character, your current score will be added to a running total."
                + "\nYou get bonus points for using clever strategies to come up with the answer in only a few guesses."
                + "\nYou can ask about more than one feature at a time, but you will only be given a \"YES\" response if ALL of the features match."
                + "\nClick the \"CARD INFO\" button to learn which features a certain character has."
                + "\nLeft-click to select a character and right-click to turn them over."
                );

                //begin logging trials
                Background.outFile.println("Begin Logging");
                startTime = Background.currentTime();
                Background.outFile.printf("Participant Start Time: %.3f", startTime);
                Background.outFile.println();
                Background.outFile.printf("Trial Start Time: %.3f", startTime);
                Background.outFile.println();
                Background.outFile.println("Trial Number: " + Background.getTrialNum());
                Background.outFile.println("----------------------------------------");
                Background.outFile.flush();
                b = true;
                Background.outFile.flush();
            }
        } while(b);//exit flag
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }//end method main

    /**
     * Pauses the game for a specific amount of time (in ms)
     * @param ms the number of milliseconds to pause the game
     */
    public static void pause (int ms) {
        try {
           Thread.sleep (ms);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    } // end method pause
}//end class Main
