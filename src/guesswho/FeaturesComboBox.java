/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guesswho;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComboBox;
//import java.awt.event.MouseListener;


/** @author Spencer */
public class FeaturesComboBox extends JComboBox implements GuessObject
{

  String[] options;
  String title;
  int index;
  boolean correct;
  private String correctAnswer;
  MouseTrap ml;
  RadioPanel panel;


  FeaturesComboBox(Feature f, int ind, MouseTrap m)
  {
    super();
    ml = m;
    panel = null;
    correct = false;
    options = f.getOptions();
    index = ind;
    title = f.getName();
    this.setMaximumRowCount(options.length + 1);
    this.addItem(title);
    for (int i = 0; i < options.length; i++)
    {
      this.addItem(options[i]);
    }

  }


  public void init()
  {
  }


  public void setRadioPanel(RadioPanel rp)
  {
    panel = rp;
  }


  public boolean hasRadioPanel()
  {
    return (panel != null);
  }


  public RadioPanel getRadioPanel()
  {
    return panel;
  }


  public void pressButton(int x, int y, int button)
  {
    this.setCorrect(false);
  }


  public int getIndex()
  {
    return index;
  }


  public String toString()
  {
    return title + " ComboBox";
  }


  public void setCorrect(boolean s)
  {
    correct = s;
    if (s == true)
    {
      correctAnswer = this.getSelectedItem().toString();
    }
  }


  public boolean getCorrectSelection()
  {
    return correct;
  }


  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // ALLOWS FOR PARTIAL INFORMATION SUPPORT
    if (GuessWho.PARTIAL_INFO)
    {
      if (Background.card != null)
      {
        if (this.getSelectedItem().equals(this.correctAnswer))
        {
          g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));
          g.fillRect(0, 0, 200, 100);
        } else
        {
          // g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));
          // g.fillRect(0, 0, 200, 100);
        }
      }
    } // end if partial info
  }
}
