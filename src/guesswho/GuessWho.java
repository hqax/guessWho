package guesswho;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/** @author Spencer Bryant */
public class GuessWho
{
  static final int START_TIME = 99;
  static int systemStartTime;
  static String startInfo;
  static final boolean PARTIAL_INFO = false;
  static double startTime;


  public static void main(String[] args)
  {
    JFrame gameFrame = new JFrame("Guess Who");

    // Set up game window
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    gameFrame.setSize(width, height);

    try
    {
      // adds game objects to game window
      Background background = new Background(gameFrame.getWidth(), gameFrame.getHeight());
      BorderLayout g = new BorderLayout();
      background.setLayout(g);
      background.init();
      gameFrame.add(background);
      gameFrame.setVisible(true);
      systemStartTime = (int) (System.currentTimeMillis() / 1000);
      boolean done = false;

      // game loop
      do
      {
        pause(40);
        background.repaint();

        if (!done)
        {
          // rules dialog
          JOptionPane.showMessageDialog(null,
              "                                          Guess Who?    \n\n"
                  + "The computer chose a character--guess which one.\n\n"
                  + "To narrow your choices, select features to the left and click ASK.\n"
                  + "Right-click cards that don't match to turn them over.\n"
                  + "You can ask about multiple features, but you'll get a YES only if all features match.\n"
                  + "Click CARD INFO to see a text list of a card's features.\n"
                  + "Wrong guesses cost 10 points. Bonus points for clever strategies that minimize narrowing!");

          // begin logging trials
          Background.outFile.println("Begin Logging");
          startTime = Background.currentTime();
          Background.outFile.printf("Participant Start Time: %.3f", startTime);
          Background.outFile.println();
          Background.outFile.printf("Trial Start Time: %.3f", startTime);
          Background.outFile.println();
          Background.outFile.println("Trial Number: " + Background.getTrialNum());
          Background.outFile.println("----------------------------------------");
          Background.outFile.flush();
          done = true;
          Background.outFile.flush();
        }
      } while (done);// exit flag
    } catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
  }


  public static void pause(int ms)
  {
    try
    {
      Thread.sleep(ms);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
