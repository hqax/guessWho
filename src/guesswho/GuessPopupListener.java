package guesswho;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


/** @author Spencer */
public class GuessPopupListener implements PopupMenuListener
{

  GuessPopupListener()
  {
    super();
  }


  @Override
  public void popupMenuCanceled(PopupMenuEvent e)
  {
  }


  @Override
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
  {
    Background.outFile.println("Event: PopupMenu Closed");
    Background.outFile.printf("Time: %.3f\n", Background.currentTime());
    Background.outFile.println("Object Clicked: " + e.getSource().toString());
    Background.outFile.println(((FeaturesComboBox) e.getSource()).getSelectedItem() + " selected from menu");
    Background.outFile.println("Current Trial: " + Background.getTrialNum());
    Background.outFile.println("Current Trial Score: " + Background.getTrialPoints());
    Background.outFile.println("Current Total Score: " + Background.getTotalPoints());
    Background.outFile.println("-------------------------");
    Background.outFile.flush();
  }


  @Override
  public void popupMenuWillBecomeVisible(PopupMenuEvent e)
  {
    Background.outFile.println("Event: PopupMenu Opened");
    Background.outFile.printf("Time: %.3f\n", Background.currentTime());
    Background.outFile.println("Object Clicked: " + e.getSource().toString());
    Background.outFile.println("Current Trial: " + Background.getTrialNum());
    Background.outFile.println("Current Trial Score: " + Background.getTrialPoints());
    Background.outFile.println("Current Total Score: " + Background.getTotalPoints());
    Background.outFile.println("-------------------------");
    Background.outFile.flush();
  }

}
