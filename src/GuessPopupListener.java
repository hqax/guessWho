
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.event.*;

/**
 *
 * @author Spencer
 */
public class GuessPopupListener implements PopupMenuListener{

    GuessPopupListener(){
        super();
    }


    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        //Background.outFile.println(((FeaturesComboBox)e.getSource()).getSelectedItem());
        //Background.outFile.flush();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        Background.outFile.println("Event: PopupMenu Closed");
        Background.outFile.printf("Time: %.3f\n", Background.currentTime());
        //Background.outFile.println("Cursor Location: (" + e.getXOnScreen() + ", " + e.getYOnScreen() + ")");
        Background.outFile.println("Object Clicked: " + e.getSource().toString());
        Background.outFile.println(((FeaturesComboBox)e.getSource()).getSelectedItem() + " selected from menu");
        //Background.outFile.println("Button Used: " + e.getButton());
        Background.outFile.println("Current Trial: " + Background.getTrialNum());
        Background.outFile.println("Current Trial Score: " + Background.getTrialPoints());
        Background.outFile.println("Current Total Score: " + Background.getTotalPoints());
        Background.outFile.println("-------------------------");
        Background.outFile.flush();
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        Background.outFile.println("Event: PopupMenu Opened");
        Background.outFile.printf("Time: %.3f\n", Background.currentTime());
        //Background.outFile.println("Cursor Location: (" + e.getXOnScreen() + ", " + e.getYOnScreen() + ")");
        Background.outFile.println("Object Clicked: " + e.getSource().toString());
        //Background.outFile.println("Button Used: " + e.getButton());
        Background.outFile.println("Current Trial: " + Background.getTrialNum());
        Background.outFile.println("Current Trial Score: " + Background.getTrialPoints());
        Background.outFile.println("Current Total Score: " + Background.getTotalPoints());
        Background.outFile.println("-------------------------");
        Background.outFile.flush();
    }

}
