/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Emergency;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class EmergencyController {
    EmergencyFrame ef;
    EmergencyService es;
    
    public EmergencyController(EmergencyFrame ef){
        this.ef = ef;
        this.ef.buttonListener(new ButtonEvent());
        es = new EmergencyServiceImplementation(ef);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ef.hm){
            es.SMSHome();
            es.save();
        }
            else if(e.getSource() == ef.hsptl){
                es.SMSHospital();
                es.save();
            }
        
        }
    }
}
