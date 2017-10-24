package guesswho;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/** @author Spencer */
class MouseHandler implements MouseListener
{

  public MouseHandler()
  {
    super();

  }


  public void mouseClicked(MouseEvent e)
  {
    Background.outFile.println("Event: Mouse Clicked");
    Background.outFile.printf("Time: %.3f", Background.currentTime());
    Background.outFile.println();
    Background.outFile.println("Cursor Location: (" + e.getXOnScreen() + ", " + e.getYOnScreen() + ")");
    Background.outFile.println("Object Clicked: " + e.getSource().toString());
    Background.outFile.println("Button Used: " + e.getButton());
    Background.outFile.println("Current Trial: " + Background.getTrialNum());
    Background.outFile.println("Current Trial Score: " + Background.getTrialPoints());
    Background.outFile.println("Current Total Score: " + Background.getTotalPoints());

    if (e.getClickCount() == 2 && e.getButton() == 1)// 2x-click and
                                                     // right-click both
                                                     // flip
    {
      Background.outFile.println("Event Type: Double Click");
      ((GuessObject) e.getSource()).pressButton(e.getXOnScreen(), e.getYOnScreen(), 3);

    } else
    {
      Background.outFile.println("Event Type: Single Click");
      ((GuessObject) e.getSource()).pressButton(e.getXOnScreen(), e.getYOnScreen(), e.getButton());
    }

    Background.outFile.println("-------------------------");
    Background.outFile.flush();
  }


  public void mousePressed(MouseEvent e)
  {
  }


  public void mouseReleased(MouseEvent e)
  {
  }


  public void mouseEntered(MouseEvent e)
  {
  }


  public void mouseExited(MouseEvent e)
  {
  }
}
