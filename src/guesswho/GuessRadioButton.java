/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guesswho;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JRadioButton;


/** @author Spencer */
@SuppressWarnings("serial")
public class GuessRadioButton extends JRadioButton implements GuessObject
{

  GuessRadioButton partner;
  boolean marked;


  public GuessRadioButton(String message)
  {
    super(message);
    marked = false;
  }


  public void pair(GuessRadioButton p)
  {
    partner = p;
  }


  public void pressButton(int x, int y, int button)
  {
    if (button == 1 && !isSelected())
    {// for unselecting a selected button
      setSelected(false);
      unmark();
    } else if (button == 1)
    {
      setSelected(true);
      partner.setSelected(false);
      partner.unmark();
    }
  }


  public String toString()
  {
    return "Radio Button: " + this.getText();
  }


  public void mark()
  {
    marked = true;
  }


  public void unmark()
  {
    marked = false;
  }


  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // ALLOWS FOR PARTIAL INFORMATION SUPPORT
    if (GuessWho.PARTIAL_INFO)
    {
      if (marked)
      {
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 20, 20);
      }
    }

  }
}
