/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Secretary;

import StaffPortal.Secretary.Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author richelle
 */
public class SecretaryController {
    SecretaryService ss;
    
    SecretaryPanel sp;
    AddSecretary as;
    UpdateSecretary us;
    
    public SecretaryController(SecretaryPanel sp, AddSecretary as, UpdateSecretary us){
        this.sp = sp;
        this.as = as;
        this.us = us;
        
        this.sp.buttonListeners(new ButtonEvent());
        this.as.buttonListeners(new ButtonEvent());
        this.us.buttonListener(new ButtonEvent());
        
        ss = new SecretaryServiceImplementation(sp,as,us);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == sp.addbtn){
                as.setVisible(true);
            }
            else if(e.getSource() == sp.editbtn){
                ss.editView();
            }
            else if(e.getSource() == sp.delbtn){
                ss.delete();
            }
            else if(e.getSource() == as.savebtn){
                ss.save();
            }
            else if(e.getSource() == us.updatebtn){
                ss.update();
            }
            
        }
        
    }
}
