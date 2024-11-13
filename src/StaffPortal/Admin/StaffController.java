/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Admin;

import StaffPortal.Admin.Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class StaffController {
    
    StaffService ss;
    StaffPanel sp;
    AddStaff as;
    UpdateStaff us;
    
    public StaffController(StaffPanel sp, AddStaff as, UpdateStaff us){
        this.sp = sp;
        this.as = as;
        this.us = us;
        
        this.sp.buttonListeners(new ButtonEvent());
        this.as.buttonListener(new ButtonEvent());
        this.us.buttonListener(new ButtonEvent());
        
        ss = new StaffServiceImplementation(sp, as, us);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == sp.dd){
                as.setVisible(true);
            }
            else if(e.getSource() == sp.pdt){
                ss.editView();
            }
            else if(e.getSource() == sp.dlt){
                ss.delete();
            }
            else if(e.getSource() == as.sv){
                ss.save();
            }
            else if(e.getSource() == us.pdt){
                ss.update();
            }
        }
        
    }
}
