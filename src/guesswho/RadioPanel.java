/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guesswho;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author Spencer
 */
public class RadioPanel extends JPanel{
    GuessRadioButton b1;
    GuessRadioButton b2;
    MouseListener m;
    RadioPanel(MouseListener ml){
        super();
        //feat = f;
        m = ml;
        b1 = new GuessRadioButton("Has:");
        b2 = new GuessRadioButton("Lacks:");
        
        b1.pair(b2);
        b2.pair(b1);
        b1.addMouseListener(m);
        b2.addMouseListener(m);
        this.add(b1);
        
        this.add(b2);
    }
    
    public int getStatus(){
        
        if (b1.isSelected()){
            return 1;
        }
        else if (b2.isSelected()){
            return 2;
        } else { //neither selected
            return -1;
        }
    }
    
    public GuessRadioButton getHasButton(){
        return b1;
    }
    
    public GuessRadioButton getLacksButton(){
        return b2;
    }
    
    
    
}
