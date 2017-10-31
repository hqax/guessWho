
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Spencer
 */
public class AskButton2 extends JButton implements GuessObject {

    //ArrayList<FeaturesComboBox> boxList;
    //Card target;

    AskButton2(String message, ArrayList<JList> bl, Card t) {
        super(message);
       // boxList = bl;
        //target = t;
    }

    public void doStuff(int x, int y, int button) {}
/*
    public void doStuff(int x, int y, int button) {
        //actually input the guess
        Background.outFile.println("Query submitted:");
        if (GuessWho.PARTIAL_INFO) {
            //shadeMenus(obtainHits());
        } else {
             int correct = areAllCorrect();

            if (correct == 1) {
                Background.deductTrialPoints(10);
            Background.setAnswer(1);
            //JOptionPane.showMessageDialog(null, "Yes");
        } else if (correct == 0){
            Background.deductTrialPoints(10);
            Background.setAnswer(0);
            //JOptionPane.showMessageDialog(null, "No");
        }else if (correct == -1){
            Background.setAnswer(-1);
            JOptionPane.showMessageDialog(this, "You did not select a radio button.  Fix it.");
        return;
        } else {
            Background.setAnswer(-1);
        }
        //all comboboxes that were correctly selected will be turned uneditable.
        }
    }

    public int areAllCorrect() {
        int unchanged = 0;
        for (int i = 0; i < Background.boxList.size(); i++) {
            if (!Background.boxList.get(i).getSelectedItem().toString().equals(
                    Background.boxList.get(i).getItemAt(0).toString())) {//if it was changed
                //if it has a radiopanel
                if (Background.boxList.get(i).hasRadioPanel()) {
                    //if any radio buttons are unselected and the title is not selected, force them to fix it
                    if (Background.boxList.get(i).getRadioPanel().getStatus() == -1){
                        Background.outFile.println("Error: need to select radio button");
                        return -1;
                    }
                    if (Background.boxList.get(i).getRadioPanel().getStatus() == 1){
                    Background.outFile.println("Selected radio button: " +
                            Background.boxList.get(i).getRadioPanel().getHasButton().getText() +
                            Background.boxList.get(i).getSelectedItem().toString());
                    } else if (Background.boxList.get(i).getRadioPanel().getStatus() == 2){
                    Background.outFile.println("Selected radio button: " +
                            Background.boxList.get(i).getRadioPanel().getLacksButton().getText() +
                            Background.boxList.get(i).getSelectedItem().toString());
                    }
                    //is the conjunction incorrect?
                    if (!(Background.boxList.get(i).getSelectedItem().toString().equals(Background.target.getCharacter().getFeatures().get(i)))
                            && (Background.boxList.get(i).getRadioPanel().getStatus() == 1)) {
                        Background.outFile.println("No Match for " + Background.boxList.get(i).getRadioPanel().getHasButton().getText() + Background.boxList.get(i).getSelectedItem().toString());
                        return 0;
                    } else if ((Background.boxList.get(i).getSelectedItem().toString().equals(Background.target.getCharacter().getFeatures().get(i)))
                            && (Background.boxList.get(i).getRadioPanel().getStatus() == 2)) {
                        Background.outFile.println("No Match for " + Background.boxList.get(i).getRadioPanel().getLacksButton().getText() + Background.boxList.get(i).getSelectedItem().toString());
                        return 0;
                    } else {
                        //correct conjunction
                        if (Background.boxList.get(i).getRadioPanel().getStatus() == 1){//has
                        Background.outFile.println("Match for " + Background.boxList.get(i).getRadioPanel().getHasButton().getText() + Background.boxList.get(i).getSelectedItem().toString());
                        } else if (Background.boxList.get(i).getRadioPanel().getStatus() == 2){//lacks
                            Background.outFile.println("Match for " + Background.boxList.get(i).getRadioPanel().getLacksButton().getText() + Background.boxList.get(i).getSelectedItem().toString());
                        }
                    }
                } else if (!Background.boxList.get(i).getSelectedItem().toString().equals(Background.target.getCharacter().getFeatures().get(i))) {
                    //does it have no panel and is wrong?
                    Background.outFile.println("No Match for " + Background.boxList.get(i).getSelectedItem().toString());
                    return 0;
                } else {
                    //must have no panel and is a match for this one feature at least
                    Background.outFile.println("Match for " + Background.boxList.get(i).getSelectedItem().toString());
                }
            } else {
                unchanged++;
            }
        }
        if (unchanged == Background.boxList.size()){
            Background.outFile.println("Defaults Selected");
            return -2;
        }
        //must be correct
        Background.outFile.println("Selected Characteristics Match");
        return 1;
    }

    public ArrayList<FeaturesComboBox> obtainHits() {
        ArrayList<String> features = Background.target.getCharacter().getFeatures();
        ArrayList<FeaturesComboBox> hits = new ArrayList();
        for (int i = 0; i < Background.boxList.size(); i++) {
            if (Background.boxList.get(i).getItemCount() > 3) {
                //get the status of the GuessRadioButtons and if 1 "has" use ==, if 2 "has" use !=
                if ((Background.boxList.get(i).getRadioPanel().getStatus() == 1)
                        && Background.boxList.get(i).getSelectedItem().equals(features.get(i))) {

                    hits.add(Background.boxList.get(i));
                } else if ((Background.boxList.get(i).getRadioPanel().getStatus() == 2)
                        && !Background.boxList.get(i).getSelectedItem().equals(features.get(i))) {

                    hits.add(Background.boxList.get(i));
                }
            } else if (Background.boxList.get(i).getSelectedItem().equals(features.get(i))) {
                hits.add(Background.boxList.get(i));
            } else {
                //System.out.println(features.get(i) + "observed");
                // System.out.println(boxes.get(i).getSelectedItem() + "selected");
                //System.out.println("no match for " + boxes.get(i).getItemAt(0));
            }
        }
        // System.out.println(target.getCharacter().getName());
        return hits;
        //TO-DO: need a more distinct correct-or-not calculation.  What should I do
        //when they select NOT BROWN HAIR?  should i limit how many questions they
        //can ask about hair color? or should I remove some from the list?  Ask Scott and Adam

    }

    public String toString() {
        return "Button: Query";
    }

    public void shadeMenus(ArrayList<FeaturesComboBox> hits) {
        for (int i = 0; i < hits.size(); i++) {
            //if it has a radiobutton, the radiobutton should also be shaded.
            if (hits.get(i).hasRadioPanel()) {
                if (hits.get(i).getRadioPanel().getHasButton().isSelected()) {
                    //button1 is selected
                    hits.get(i).getRadioPanel().getHasButton().mark();
                    hits.get(i).getRadioPanel().getLacksButton().unmark();
                } else if (hits.get(i).getRadioPanel().getLacksButton().isSelected()) {
                    //button 2 is selected
                    hits.get(i).getRadioPanel().getLacksButton().mark();
                    hits.get(i).getRadioPanel().getHasButton().unmark();
                } else {
                    //neither is selected
                }
            }
            hits.get(i).setCorrect(true);
        }
    }
    */
}
