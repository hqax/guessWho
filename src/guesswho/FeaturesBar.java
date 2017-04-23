package guesswho;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

class FeaturesBar extends JPanel{

  public ArrayList<Feature> featuresSet;
  public static ArrayList<JList> jListList;
  private final int BORDER_SIZE = 2;
  private final int NUM_OF_FEATURE_COLS = 3;
  private final int NUM_OF_FEATURE_ROWS = 4;

	private static MouseHandler mouseHandler;

	public FeaturesBar(){
		super();
    featuresSet = new ArrayList<Feature>();
		jListList = new ArrayList<JList>();
		buildFeatures();
		setLayout(new GridLayout(NUM_OF_FEATURE_ROWS, NUM_OF_FEATURE_COLS));
		setBorder(new LineBorder(Color.black, BORDER_SIZE));
		initFeaturesBar();
	}

	public void addMouseListener(MouseHandler mouseHandler){
		this.mouseHandler = mouseHandler;
	}

	public void buildFeatures() {
		featuresSet.add(new SkinColorFeature());
		featuresSet.add(new EyeColorFeature());
		featuresSet.add(new SexFeature());
		featuresSet.add(new MouthShapeFeature());
		featuresSet.add(new LipsFeature());
		featuresSet.add(new HairFeature());
		featuresSet.add(new BeardFeature());
		featuresSet.add(new MustacheFeature());
		featuresSet.add(new NoseFeature());
		featuresSet.add(new ShirtFeature());
		featuresSet.add(new HatFeature());
		featuresSet.add(new EyewearFeature());
	}

	public void initFeaturesBar() {
		for (int feature = 0; feature < featuresSet.size(); feature++) {
			JPanel featurePanel = new JPanel();
			featurePanel.setLayout(new GridLayout(1, 1));

      Feature currentFeature = featuresSet.get(feature);
			GuessList currentFeatureSelector = new GuessList(
        currentFeature.getOptions(), currentFeature.getName()
      );
			currentFeatureSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			jListList.add(currentFeatureSelector);
			currentFeatureSelector.addMouseListener(mouseHandler);
			featurePanel.add(currentFeatureSelector);
			add(featurePanel);
		}
	}

	public void clearFeatures(){
    for (int j = 0; j < jListList.size(); j++) {
      jListList.get(j).setSelectedIndex(0);
      jListList.get(j).clearSelection();
    }
	}
}
