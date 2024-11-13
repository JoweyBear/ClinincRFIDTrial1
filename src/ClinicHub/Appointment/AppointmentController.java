/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class AppointmentController {
    AppointmentPanel ap;
    AppointmentService as;
    
    public AppointmentController(AppointmentPanel ap){
        this.ap = ap;
        this.ap.buttonListener(new ButtonEvent());
        as = new AppointmentServiceImplementation(ap);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ap.cnfrm){
                as.SMS();
            }
        }
        
    }
}
