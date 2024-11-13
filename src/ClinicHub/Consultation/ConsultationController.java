/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Consultation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class ConsultationController {
    ConsultationFrame cf;
    ConsultationService cs;
    
    public ConsultationController(ConsultationFrame cf){
        this.cf = cf;
        this.cf.buttonListener(new ButtonEvent());
        cs = new ConsultationServiceImplementation(cf);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == cf.rcrd){
                cs.save();
            }
            else if(e.getSource() == cf.ntfy){
                cs.SMS();
                cs.save();
            }
        }
        
    }
}
