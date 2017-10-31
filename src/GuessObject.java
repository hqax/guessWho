
import java.awt.*;
import java.awt.Font;
import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Spencer
 */
abstract interface GuessObject {

    public abstract void doStuff(int x, int y, int button);

    public abstract String toString();
}

//------------------------------------------------------------------------------

class InfoButton extends JButton implements GuessObject {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Character selected;

    InfoButton(String message, Character c) {
        super(message);
        selected = c;
    }

    public void setSelected(Character ch) {
        selected = ch;
    }

    public void doStuff(int x, int y, int Button) {
        if (selected != null) {
            //   System.out.println("selected = " + Background.selected.getCharacter().getName());
            Background.outFile.println("Request Info, Character: " + selected.getName());
            JFrame infoWindow = new JFrame("Info");
            JPanel infoBG = new JPanel();
            infoBG.add(new JLabel(selected.getName()));
            //makes a list of features for a specific card
            infoBG.setLayout(new GridLayout(selected.getFeatures().length + 1, 1));
            //adds the features to the list on the window
            for (int i = 0; i < selected.getFeatures().length; i++){
                infoBG.add(new JLabel(selected.getFeatures()[i]));
            }
            infoWindow.add(infoBG);
            infoWindow.setSize(200, 500);
            infoWindow.setVisible(true);
        }
    }

    public String toString() {
        return "Button: Info";
    }
}

//------------------------------------------------------------------------------
class InstructionButton extends JButton implements GuessObject{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JFrame instructionsWindow;
    JPanel messageLabel;

    InstructionButton(String message){
        super(message);

    }

    public void doStuff(int x, int y, int button){

                instructionsWindow = new JFrame("Instructions");
                messageLabel = new JPanel();
                messageLabel.setLayout(new GridLayout(4, 1));
                //messageLabel.setBackground(Color.ORANGE);
                messageLabel.add(new JLabel("Right-click  or double-click a FEATURE to deselect it."), 0);
                messageLabel.add(new JLabel("Right-click  or double-click a CARD to turn it face-down."), 0);
                messageLabel.add(new JLabel("Left-click a CARD to select or deselect it."), 0);
                messageLabel.add(new JLabel("Left-click a FEATURE to select it."), 0);

                instructionsWindow.add(messageLabel);
                instructionsWindow.setLocation(200, 500);
                instructionsWindow.setSize(450, 150);
                //dookieWindow.setLocation(x, y);
                instructionsWindow.setVisible(true);
    }

    public String toString(){
        return "Button: Instructions";
    }
}

//------------------------------------------------------------------------------

class GiveUpButton extends JButton implements GuessObject{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	GiveUpButton(String message){
        super(message);

    }

    public void doStuff(int x, int y, int Button){


                if (JOptionPane.showConfirmDialog(null, "Are you absolutely sure?") == JOptionPane.OK_OPTION){
                JOptionPane.showMessageDialog(null, "The answer was " + Background.target.getCharacter().getName());
                //Background.deductTrialPoints(Background.getTrialPoints());
                Background.reset();
                }
    }

    public String toString(){
        return "Button: Give Up - Answer: " + Background.target.getCharacter().getName();
    }
}

//------------------------------------------------------------------------------
class FinalGuessButton extends JButton implements GuessObject {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Character person;

    FinalGuessButton(String message, Character c) {
        super(message);
        person = c;

    }

    public void doStuff(int x, int y, int Button) {
        if (JOptionPane.showConfirmDialog(null, "Are you absolutely sure?") == JOptionPane.YES_OPTION) {
            // System.out.println("target = " + person.getName());
            if (Background.selected != null) {
                //   System.out.println("selected = " + Background.selected.getCharacter().getName());
                Background.outFile.println("Final Guess, Player Answer: " + Background.selected.getCharacter().getName());

                if (Background.selected.getCharacter() == person) {
                    JOptionPane.showMessageDialog(null, "CORRECT!! The answer was " + person.getName());
                    Background.outFile.println("Correct Answer: " + Background.target.getCharacter().getName());
                    Background.increaseTotalPoints(Background.getTrialPoints());

                    if (Background.getTrialPoints() >= 20) {
                        Background.increaseTotalPoints(Background.getTrialPoints() * .5 );
                    }
                    Background.reset();
                } else {
                    JOptionPane.showMessageDialog(null, "WRONG!! The answer was " + person.getName());
                    Background.outFile.println("Correct Answer: " + Background.target.getCharacter().getName());
                    //Background.deductTrialPoints(Background.getTrialPoints());
                    Background.reset();
                }
            } else {

                Background.outFile.println("Final Guess, Player Answer: None Selected");
                JOptionPane.showMessageDialog(null, "WRONG!! The answer was " + person.getName());
                Background.outFile.println("Correct Answer: " + Background.target.getCharacter().getName());
                //Background.deductTrialPoints(Background.getTrialPoints());
                Background.reset();
            }
            person = Background.target.getCharacter();
        }
    }

    public String toString() {
        return "Button: Final Guess";
    }
}

//------------------------------------------------------------------------------

class GuessList extends JList implements GuessObject{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int selectedIndex;
    private String name;
    GuessList(){
        super();
        selectedIndex = 10;
    }
    GuessList(String[] s, String n){
        super(s);
        name = n;
    }

    public String toString(){
    	String n = this.name;
    	String s = (String)this.getSelectedValue();
    	return n + " - " + s;
    }
    public void doStuff(int x, int y, int button){
        if (button == 3){
        if (this.getSelectedIndex() == selectedIndex){
           clearSelection();
        }else {
            selectedIndex = this.getSelectedIndex();
        }
        }

    }
}
//------------------------------------------------------------------------------

class Card extends JLabel implements GuessObject {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
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

    public Character getCharacter() {
        return character;
    }

    public ImageIcon[] getCardFace() {
        return showingFace;
    }

    public void setCardfront(ImageIcon[] cardfront) {
        this.cardfront = cardfront;
    }

    public void setTileHeight(int height) {
        tileHeight = height;
    }

    public void setTileWidth(int width) {
        tileWidth = width;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public Color getBorderColor() {
        return rectColor;
    }

    Card() {
        super();
        character = new Character();

        //portraitLabel = new JLabel(character.getName(), 20);
        //add(portraitLabel);
        faded = false;
        alpha = 1.0f;
        tileHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 7/24;
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

    public boolean isMarked() {
        return marked;
    }

    public boolean isFaded(){
        return faded;
    }

    public void doStuff(int x, int y, int button){
        switch(button){
            case -1:
                fade();
                break;
            case 3:
                flip();
                unmark();
                break;
            case 1:
                for (int i = 0; i < Background.deck.size(); i++) {//un-click others
                    if (Background.deck.get(i) != this) {
                            Background.deck.get(i).unfade();
                    }
                }
                fade();
                Background.info.setSelected(this.getCharacter());
                break;
        }
    }

    public void flip() {
        showingFace = (showingFace.equals(cardfront)) ? cardback : cardfront;
    }

    public void unflip(){
        showingFace = cardfront;
    }

    public void fade() {
        marked = false;
        alpha = (faded == false) ? 0.0f : 1.0f;
        faded = (faded == false) ? true : false;
        Background.selected = this;
    }
    public void mark() {
        marked = (marked == false) ? true : false;
        Background.info.setSelected(this.getCharacter());
    }

    public void unmark(){
        marked = false;
    }

    public void unfade() {
        alpha = 0.0f;
        faded = false;
    }
    public String toString(){
        return this.getCharacter().getName();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isMarked()) {
            g.setColor(Color.black);
        } else {
            g.setColor(new Color(200, 200, 0));
        }
        g.fillRect(0, 0, getTileWidth() + 10, getTileHeight() + 10);

        for (int i = 0; i < showingFace.length; i++) {
            g.drawImage(showingFace[i].getImage(), 5, 5, getTileWidth(), getTileHeight() * 7 / 8, null);
        }
        if (isFaded()){
            g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
            g.fillRect(5, 5, getTileWidth(), getTileHeight() * 7 / 8);
        }
        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString(character.getName(), 10, getTileHeight());
    } // end method paintComponent
}
//------------------------------------------------------------------------------

class AskButton extends JButton implements GuessObject{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<JList> listList;

    AskButton(String message, ArrayList<JList> ll) {
        super(message);
        listList = ll;
    }

    public void doStuff(int x, int y, int button) {
        //actually input the guess
        Background.outFile.println("Query submitted:");
        int correct = areAllCorrect();
        if (correct == 1) {
            Background.deductTrialPoints(1);
            Background.setAnswer(1);
        } else if (correct == 0) {
            Background.deductTrialPoints(1);
            Background.setAnswer(0);
        } else {
            Background.setAnswer(-1);
        }
        for (int i = 0; i < Background.jListList.size(); i++) {
            Background.jListList.get(i).clearSelection();
        }
    }

    public int areAllCorrect() {
        int unchanged = 0;
        for (int i = 0; i < Background.jListList.size(); i++) {
            if (!Background.jListList.get(i).isSelectionEmpty()) {
                //if it was changed
                //if (!Background.jListList.get(i).getSelectedValue().toString().equals(Background.target.getCharacter().getFeatures()[i])) {
                if (!Background.jListList.get(i).getSelectedValue().toString().equals(Background.target.getCharacter().getFeatures()[i])) {
                    //is it wrong?
                    Background.outFile.println("No Match for " + Background.jListList.get(i).getSelectedValue().toString());
                    return 0;
                } else {
                    //must be a match for this one feature at least
                    Background.outFile.println("Match for " + Background.jListList.get(i).getSelectedValue().toString());
                }
            } else {
                unchanged++;
            }
        }
        if (unchanged == Background.jListList.size()) {
            Background.outFile.println("Nothing Selected");
            return -2;
        }
        //must be entirely correct
        Background.outFile.println("Selected Characteristics Match");
        return 1;
    }

    public String toString() {
        return "Button: Query";
    }
}
