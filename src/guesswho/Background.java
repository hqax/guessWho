package guesswho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


/** @author Spencer */
class Background extends JLayeredPane
{
  private static final long serialVersionUID = 1L;

  private static MouseHandler mHandler;

  private InstructionButton instructions;
  private FinalGuessButton finalGuess;
  private AskButton ask;
  private GiveUpButton giveUp;
  static InfoButton info;

  private JPanel payoutBar;
  private JPanel optionsBar;
  private JPanel buttonBar;
  private JPanel featuresBar;
  private static JPanel playArea;
  private static JLabel totalPayoutLabel;
  private static JLabel trialPayoutLabel;
  private static JLabel answer;

  private final int CARDS_PER_ROW = 6;
  private final int CARDS_PER_COLUMN = 3;
  private final int BORDER_SIZE = 2;
  private final int NUM_OF_OPTIONS_BUTTONS_DOWN = 3;
  private final int NUM_OF_OPTIONS_BUTTONS_ACROSS = 2;
  private static final int NUMBER_OF_STARTING_GUESSES = 6;

  private ArrayList<Feature> featuresSet;
  static ArrayList<Card> deck;
  static ArrayList<JList> jListList;
  public static ArrayList<String> boyNames;
  public static ArrayList<String> girlNames;

  private final Color FOREGROUND_TEXT_COLOR = new Color(0, 150, 0);

  private static int points;
  private static int guesses;
  private static int trialNumber;

  static Card card;
  static Card selected;

  private int height;
  private int width;

  static PrintWriter outFile;
  public static MersenneTwister mt;
  public static FileWriter fw;
  public static final String LOG_FOLDER = "logs";


  public Background(int h, int w) throws IOException
  {
    boyNames = populateBoyNames();
    girlNames = populateGirlNames();
    height = h;
    width = w;
    trialNumber = 0;
    fw = new FileWriter(LOG_FOLDER + "/log" + getTrialNum() + ".txt");
    outFile = new PrintWriter(fw);
    points = 0;
    guesses = NUMBER_OF_STARTING_GUESSES;
    mHandler = new MouseHandler();
    deck = new ArrayList<Card>();
    featuresBar = new JPanel();
    optionsBar = new JPanel();
    playArea = new JPanel();
    featuresSet = new ArrayList<Feature>();
    jListList = new ArrayList<JList>();
    mt = new MersenneTwister();
  }


  public static void deductTrialPoints(int points)
  {
    if ((guesses -= points) >= 0)
    {
      trialPayoutLabel.setText(String.format("%d Guesses", guesses));
    } else
    {
      guesses = 0;
      trialPayoutLabel.setText(String.format("%d Current", guesses));
    }
  }


  public static int getTrialPoints()
  {
    return guesses;
  }


  public static int getTrialNum()
  {
    return trialNumber;
  }


  public static int getTotalPoints()
  {
    return points;
  }


  public static void increaseTotalPoints(double pts)
  {
    points += pts;
    totalPayoutLabel.setText(String.format("%d Points", points));
  }


  public void init()
  {
    initPlayArea();
    buildFeatures();
    initOptionsBar();
  }


  public void buildFeatures()
  {
    featuresSet.add(new Feature("Skin Color", new String[] { "light skin", "dark skin" }));
    featuresSet.add(
        new Feature("Eye Color", new String[] { "blue eyes", "black eyes", "brown eyes", "green eyes", "grey eyes" }));
    featuresSet.add(new Feature("Sex", new String[] { "boy", "girl" }));
    featuresSet.add(new Feature("Mouth", new String[] { "smiling", "frowning" }));
    featuresSet.add(new Feature("Lips", new String[] { "big lips", "thin lips" }));
    featuresSet.add(new Feature("Hair",
        new String[] { "blonde hair", "black hair", "brown hair", "red hair", "grey hair", "bald" }));
    featuresSet.add(new Feature("Beard", new String[] { "beard", "no beard" }));
    featuresSet.add(new Feature("Mustache", new String[] { "mustache", "no mustache" }));
    featuresSet.add(new Feature("Nose", new String[] { "big nose", "short nose", "thin nose" }));
    featuresSet.add(new Feature("Shirt", new String[] { "blue shirt", "black shirt", "red shirt", "green shirt",
        "orange shirt", "yellow shirt", "purple shirt" }));
    featuresSet.add(new Feature("Headwear", new String[] { "hat", "no hat" }));
    featuresSet.add(new Feature("Eyewear", new String[] { "glasses", "no glasses" }));
  }


  public void initOptionsBar()
  {
    info = new InfoButton("Card Info", null);
    info.addMouseListener(mHandler);
    instructions = new InstructionButton("Instructions");
    instructions.addMouseListener(mHandler);
    ask = new AskButton("Ask", jListList);
    ask.addMouseListener(mHandler);
    finalGuess = new FinalGuessButton("Final Guess", card.getCharacter());
    finalGuess.addMouseListener(mHandler);
    giveUp = new GiveUpButton("Give Up?");
    giveUp.addMouseListener(mHandler);

    optionsBar.setLayout(new BorderLayout());
    optionsBar.setPreferredSize(new Dimension(width / 3, height));
    optionsBar.setBorder(new LineBorder(Color.black, 3));
    payoutBar = new JPanel();
    payoutBar.setLayout(new BorderLayout());
    payoutBar.setBorder(new LineBorder(Color.black, 3));
    initTotalPayoutLabel();
    initTrialPayoutLabel();
    payoutBar.add(totalPayoutLabel, BorderLayout.NORTH);
    payoutBar.add(trialPayoutLabel, BorderLayout.SOUTH);
    optionsBar.add(payoutBar, BorderLayout.NORTH);
    initFeaturesBar();
    initButtons();
    this.add(optionsBar, BorderLayout.WEST);
  }


  public void set()
  {
    playArea.removeAll();
    shuffle(deck);
    newTarget();
    for (int i = 0; i < deck.size(); i++)
    {
      deck.get(i).addMouseListener(mHandler);
      playArea.add(deck.get(i));
    }
  }


  public static void reset()
  {
    trialNumber++;
    outFile.close();
    try
    {
      fw = new FileWriter(LOG_FOLDER + "/log" + getTrialNum() + ".txt");
    } catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    outFile = new PrintWriter(fw);
    Background.outFile.println("Begin Logging\n");
    Background.outFile.printf("Participant Start Time: %.3f", GuessWho.startTime);
    Background.outFile.println();
    Background.outFile.printf("Trial Start Time: %.3f", currentTime());
    Background.outFile.println();
    Background.outFile.println("Trial Number: " + Background.getTrialNum());
    Background.outFile.flush();
    selected = null;
    playArea.removeAll();
    girlNames = populateGirlNames();
    boyNames = populateBoyNames();
    shuffle(deck);
    newTarget();
    for (int i = 0; i < deck.size(); i++)
      deck.get(i).addMouseListener(mHandler);
    Background.setAnswer(-1);
    Background.deductTrialPoints(0);
    for (int i = 0; i < deck.size(); i++)
      playArea.add(deck.get(i));
    guesses = NUMBER_OF_STARTING_GUESSES;
    trialPayoutLabel.setText(String.format("%d Guesses", guesses));
    for (int j = 0; j < jListList.size(); j++)
      jListList.get(j).setSelectedIndex(0);
    for (int i = 0; i < Background.jListList.size(); i++)
      Background.jListList.get(i).clearSelection();
  }


  public static void setAnswer(int i)
  {
    answer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    if (i == 1)
    {
      answer.setForeground(new Color(0, 100, 0));
      answer.setText("Yes");
    } else if (i == 0)
    {
      answer.setForeground(new Color(100, 0, 0));
      answer.setText("No");
    } else
    {
      answer.setText("");
    }
  }


  public static void shuffle(ArrayList<Card> d)
  {
    ArrayList<Card> newDeck = new ArrayList<Card>();
    int sz = d.size();
    while (!d.isEmpty())
      d.remove(0);
    for (int i = 0; i < sz; i++)
      newDeck.add(new Card());
    deck = newDeck;
  }


  public static void newTarget()
  {
    int r = mt.nextInt(deck.size());
    card = deck.get(r);
  }


  public void initTotalPayoutLabel()
  {
    totalPayoutLabel = new JLabel();
    totalPayoutLabel.setBorder(new LineBorder(Color.black, BORDER_SIZE));
    totalPayoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
    totalPayoutLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
    totalPayoutLabel.setForeground(FOREGROUND_TEXT_COLOR);
    totalPayoutLabel.setText(String.format("%d Points", points));
  }


  public void initTrialPayoutLabel()
  {
    trialPayoutLabel = new JLabel();
    trialPayoutLabel.setBorder(new LineBorder(Color.black, 2));
    trialPayoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
    trialPayoutLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    trialPayoutLabel.setForeground(FOREGROUND_TEXT_COLOR);
    trialPayoutLabel.setText(String.format("%d Guesses", guesses));
  }


  private static ArrayList<String> populateBoyNames()
  {
    ArrayList<String> names = new ArrayList<String>();
    names.add("james");
    names.add("lars");
    names.add("jason");
    names.add("kirk");
    names.add("tony");
    names.add("ozzy");
    names.add("lemmy");
    names.add("joe");
    names.add("steve");
    names.add("axl");
    names.add("duff");
    names.add("slash");
    names.add("jimi");
    names.add("rodney");
    names.add("joe");
    names.add("dave");
    names.add("dean");
    names.add("evan");
    names.add("bruce");
    names.add("clark");
    names.add("peter");
    names.add("stan");
    names.add("wade");
    names.add("ajax");
    names.add("sterling");

    return names;
  }


  public static String randomBoyName()
  {
    int r = mt.nextInt(boyNames.size());
    return boyNames.remove(r);
  }


  public static String randomGirlName()
  {
    int r = mt.nextInt(girlNames.size());
    return girlNames.remove(r);
  }


  private static ArrayList<String> populateGirlNames()
  {
    ArrayList<String> names = new ArrayList<String>();
    names.add("annie");
    names.add("sally");
    names.add("penny");
    names.add("jamie");
    names.add("amy");
    names.add("katie");
    names.add("cassie");
    names.add("julie");
    names.add("tammy");
    names.add("bobbie");
    names.add("debbie");
    names.add("sammie");
    names.add("stephie");
    names.add("maggie");
    names.add("lizzy");
    names.add("izzy");
    names.add("cammie");
    names.add("kaylee");
    names.add("pammy");
    names.add("dani");
    names.add("fanny");
    names.add("janny");
    names.add("pattie");
    names.add("gabbie");
    names.add("abby");

    return names;
  }


  public void initFeaturesBar()
  {
    answer = new JLabel("");
    answer.setBorder(new LineBorder(Color.BLACK, BORDER_SIZE));
    featuresBar.setLayout(new GridLayout(4, 2));
    featuresBar.setBorder(new LineBorder(Color.black, BORDER_SIZE));
    for (int i = 0; i < featuresSet.size(); i++)
    {
      JPanel j = new JPanel();
      j.setLayout(new GridLayout(1, 1));
      String[] data = featuresSet.get(i).getOptions();
      GuessList a = new GuessList(data, featuresSet.get(i).getName());
      a.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      jListList.add(a);
      a.addMouseListener(mHandler);
      j.add(jListList.get(i));
      featuresBar.add(j);
    }
    answer.setHorizontalAlignment(SwingConstants.CENTER);
    answer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    answer.setForeground(FOREGROUND_TEXT_COLOR);
    optionsBar.add(featuresBar, BorderLayout.CENTER);
  }


  public void initButtons()
  {
    buttonBar = new JPanel();
    buttonBar.setBorder(new LineBorder(Color.black, BORDER_SIZE));
    buttonBar.setLayout(new GridLayout(NUM_OF_OPTIONS_BUTTONS_DOWN, NUM_OF_OPTIONS_BUTTONS_ACROSS));
    buttonBar.add(ask);
    buttonBar.add(answer);
    buttonBar.add(finalGuess);
    buttonBar.add(info);
    buttonBar.add(giveUp);
    buttonBar.add(instructions);
    optionsBar.add(buttonBar, BorderLayout.SOUTH);
  }


  public void initPlayArea()
  {
    GridLayout grid = new GridLayout(CARDS_PER_COLUMN, CARDS_PER_ROW);
    playArea.setLayout(grid);
    this.add(playArea, BorderLayout.CENTER);
    // prints CARDS_PER ROW cards in a single row * CARDS_PER_COLUMN columns
    for (int a = 0; a < CARDS_PER_ROW; a++)
    {
      for (int b = 0; b < CARDS_PER_COLUMN; b++)
      {
        deck.add(new Card());
      }
    }
    set();
  }


  public static double currentTime()
  {
    return (System.currentTimeMillis() / 1000.0) - GuessWho.systemStartTime;
  }
}// end class Background

// TODO drastically cut size of class
// TODO single responsibility - hold items of Background
// TODO move all logic into GuessWho.java or into classes that make sense
// (Deck should know how to shuffle)
// Move initialization of optionsbar, etc into their respective classes
