package guesswho;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/** @author Spencer */
abstract interface GuessObject
{
  public abstract void pressButton(int x, int y, int button);


  public abstract String toString();
}



// ------------------------------------------------------------------------------

@SuppressWarnings("serial")
class InfoButton extends JButton implements GuessObject
{
  Character selected;


  InfoButton(String message, Character c)
  {
    super(message);
    selected = c;
  }


  public void setSelected(Character ch)
  {
    selected = ch;
  }


  public void pressButton(int x, int y, int Button)
  {
    if (selected != null)
    {
      Background.outFile.println("Request Info, Character: " + selected.getName());
      JFrame infoWindow = new JFrame("Info");
      JPanel infoBG = new JPanel();
      infoBG.add(new JLabel(selected.getName()));
      // makes a list of features for a specific card
      infoBG.setLayout(new GridLayout(selected.getFeatures().length + 1, 1));
      // adds the features to the list on the window
      for (int i = 0; i < selected.getFeatures().length; i++)
        infoBG.add(new JLabel(selected.getFeatures()[i]));
      infoWindow.add(infoBG);
      infoWindow.setSize(200, 500);
      infoWindow.setVisible(true);
    }
  }


  public String toString()
  {
    return "Button: Info";
  }
}



// ------------------------------------------------------------------------------
class InstructionButton extends JButton implements GuessObject
{

  private static final long serialVersionUID = 1L;
  JFrame instructionsWindow;
  JPanel messageLabel;


  InstructionButton(String message)
  {
    super(message);
  }


  public void pressButton(int x, int y, int button)
  {
    instructionsWindow = new JFrame("Instructions");
    messageLabel = new JPanel();
    messageLabel.setLayout(new GridLayout(4, 1));
    // messageLabel.setBackground(Color.ORANGE);
    messageLabel.add(new JLabel("Click a card to select or deselect it."), 0);
    messageLabel.add(new JLabel("Right-click or double-click a card to turn it face-down."), 0);
    messageLabel.add(new JLabel("Click a feature to select it."), 0);
    messageLabel.add(new JLabel("Right-click or double-click a feature to deselect it."), 0);

    instructionsWindow.add(messageLabel);
    instructionsWindow.setLocation(200, 500);
    instructionsWindow.setSize(450, 150);
    instructionsWindow.setVisible(true);
  }


  public String toString()
  {
    return "Button: Instructions";
  }
}



@SuppressWarnings("serial")
// ------------------------------------------------------------------------------

class GiveUpButton extends JButton implements GuessObject
{

  GiveUpButton(String message)
  {
    super(message);
  }


  public void pressButton(int x, int y, int Button)
  {
    if (JOptionPane.showConfirmDialog(null, "Are you sure?") == JOptionPane.OK_OPTION)
    {
      JOptionPane.showMessageDialog(null, "The answer was " + Background.card.getCharacter().getName());
      Background.reset();
    }
  }


  public String toString()
  {
    return "Button: Give Up - Answer: " + Background.card.getCharacter().getName();
  }
}



// ------------------------------------------------------------------------------
class FinalGuessButton extends JButton implements GuessObject
{

  private static final long serialVersionUID = 1L;
  Character person;


  FinalGuessButton(String message, Character c)
  {
    super(message);
    person = c;
  }


  public void pressButton(int x, int y, int Button)
  {
    if (JOptionPane.showConfirmDialog(null, "Are you sure?") == JOptionPane.YES_OPTION)
    {
      if (Background.selected != null)
      {
        Background.outFile.println("Final Guess, Player Answer: " + Background.selected.getCharacter().getName());
        if (Background.selected.getCharacter() == person)
        {
          JOptionPane.showMessageDialog(null, "Correct!! The answer was " + person.getName());
          Background.outFile.println("Correct Answer: " + Background.card.getCharacter().getName());
          Background.increaseTotalPoints(Background.getTrialPoints());
          if (Background.getTrialPoints() >= 20)
            Background.increaseTotalPoints(Background.getTrialPoints() * .5);
          Background.reset();
        } else
        {
          JOptionPane.showMessageDialog(null, "Wrong!! The answer was " + person.getName());
          Background.outFile.println("Correct answer: " + Background.card.getCharacter().getName());
          Background.reset();
        }
      } else
      {

        Background.outFile.println("Guess, Player Answer: None Selected");
        JOptionPane.showMessageDialog(null, "Wrong!! The answer was " + person.getName());
        Background.outFile.println("Correct answer: " + Background.card.getCharacter().getName());
      }
      person = Background.card.getCharacter();
    }
  }


  public String toString()
  {
    return "Button: Guess";
  }
}



@SuppressWarnings({ "rawtypes", "serial" })
// ------------------------------------------------------------------------------

class GuessList extends JList implements GuessObject
{
  private int selectedIndex;
  private String name;


  GuessList()
  {
    super();
    selectedIndex = 10;
  }


  GuessList(String[] s, String n)
  {
    super(s);
    name = n;
  }


  public String toString()
  {
    String n = this.name;
    String s = (String) this.getSelectedValue();
    return n + " - " + s;
  }


  public void pressButton(int x, int y, int button)
  {
    if (button == 3)
    {
      if (this.getSelectedIndex() == selectedIndex)
      {
        clearSelection();
      } else
      {
        selectedIndex = this.getSelectedIndex();
      }
    }

  }
}
// ------------------------------------------------------------------------------



@SuppressWarnings("serial")
class Card extends JLabel implements GuessObject
{
  private int tileHeight;
  private int tileWidth;
  private float alpha;
  private ImageIcon[] cardback;
  private ImageIcon[] cardfront;
  private ImageIcon[] showingFace;
  private Character character;
  private Color rectColor;
  private boolean marked;
  private boolean faded;
  private String folderName;


  public Card()
  {
    super();
    character = new Character();
    faded = false;
    alpha = 1.0f;
    tileHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 7 / 24;
    tileWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 10;
    folderName = "images/";
    cardfront = character.getPortrait();
    cardback = new ImageIcon[1];
    cardback[0] = new ImageIcon(folderName + "cardback.jpg");
    showingFace = cardfront;
    rectColor = Color.green;
    marked = false;
    AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
  }


  public Character getCharacter()
  {
    return character;
  }


  public ImageIcon[] getCardFace()
  {
    return showingFace;
  }


  public void setCardfront(ImageIcon[] cardfront)
  {
    this.cardfront = cardfront;
  }


  public void setTileHeight(int height)
  {
    tileHeight = height;
  }


  public void setTileWidth(int width)
  {
    tileWidth = width;
  }


  public int getTileHeight()
  {
    return tileHeight;
  }


  public int getTileWidth()
  {
    return tileWidth;
  }


  public Color getBorderColor()
  {
    return rectColor;
  }


  public boolean isMarked()
  {
    return marked;
  }


  public boolean isFaded() // bruh
  {
    return faded;
  }


  public void pressButton(int x, int y, int button)
  {
    switch (button)
    {
    case -1:
      fade();
      break;
    case 3:
      flip();
      unmark();
      break;
    case 1:
      for (int i = 0; i < Background.deck.size(); i++)
      {// un-click others
        if (Background.deck.get(i) != this)
        {
          Background.deck.get(i).unfade();
        }
      }
      fade();
      Background.info.setSelected(this.getCharacter());
      break;
    }
  }


  public void flip()
  {
    showingFace = (showingFace.equals(cardfront)) ? cardback : cardfront;
  }


  public void unflip()
  {
    showingFace = cardfront;
  }


  public void fade()
  {
    marked = false;
    alpha = (faded == false) ? 0.0f : 1.0f;
    faded = (faded == false) ? true : false;
    Background.selected = this;
  }


  public void mark()
  {
    marked = (marked == false) ? true : false;
    Background.info.setSelected(this.getCharacter());
  }


  public void unmark()
  {
    marked = false;
  }


  public void unfade()
  {
    alpha = 0.0f;
    faded = false;
  }


  public String toString()
  {
    return this.getCharacter().getName();
  }


  @Override
  public void paintComponent(Graphics g)
  {
    if (isMarked())
    {
      g.setColor(Color.black);
    } else
    {
      g.setColor(new Color(200, 200, 0));
    }
    g.fillRect(0, 0, getTileWidth() + 10, getTileHeight() + 10);

    for (int i = 0; i < showingFace.length; i++)
    {
      g.drawImage(showingFace[i].getImage(), 5, 5, getTileWidth(), getTileHeight() * 7 / 8, null);
    }
    if (isFaded())
    {
      g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
      g.fillRect(5, 5, getTileWidth(), getTileHeight() * 7 / 8);
    }
    g.setColor(Color.black);
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    g.drawString(character.getName(), 10, getTileHeight());
  } // end method paintComponent
}
// ------------------------------------------------------------------------------



class AskButton extends JButton implements GuessObject
{

  private static final long serialVersionUID = 1L;
  ArrayList<JList> listList;


  AskButton(String message, ArrayList<JList> ll)
  {
    super(message);
    listList = ll;
  }


  public void pressButton(int x, int y, int button)
  {
    // actually input the guess
    Background.outFile.println("Query submitted:");
    int correct = areAllCorrect();
    if (correct == 1)
    {
      Background.deductTrialPoints(1);
      Background.setAnswer(1);
    } else if (correct == 0)
    {
      Background.deductTrialPoints(1);
      Background.setAnswer(0);
    } else
    {
      Background.setAnswer(-1);
    }
    for (int i = 0; i < Background.jListList.size(); i++)
    {
      Background.jListList.get(i).clearSelection();
    }
  }


  public int areAllCorrect()
  {
    int unchanged = 0;
    for (int i = 0; i < Background.jListList.size(); i++)
    {
      if (!Background.jListList.get(i).isSelectionEmpty())
      {
        // if it was changed
        if (!Background.jListList.get(i).getSelectedValue().toString()
            .equals(Background.card.getCharacter().getFeatures()[i]))
        {
          // is it wrong?
          Background.outFile.println("No Match for " + Background.jListList.get(i).getSelectedValue().toString());
          return 0;
        } else
        {
          // must be a match for this one feature at least
          Background.outFile.println("Match for " + Background.jListList.get(i).getSelectedValue().toString());
        }
      } else
      {
        unchanged++;
      }
    }
    if (unchanged == Background.jListList.size())
    {
      Background.outFile.println("Nothing Selected");
      return -2;
    }
    // must be entirely correct
    Background.outFile.println("Selected Characteristics Match");
    return 1;
  }


  public String toString()
  {
    return "Button: Query";
  }
}
