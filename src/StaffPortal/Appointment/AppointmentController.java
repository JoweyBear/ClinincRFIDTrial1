/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Appointment;

import StaffPortal.Appointment.Views.*;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class AppointmentController {
        AppointmentService as;
        AddAppointment aa;
        EditAppointment ea;
        AppointmentPanel ap;
        
        public AppointmentController(AppointmentPanel ap, AddAppointment aa, EditAppointment ea){
        
            this.ap = ap;
            this.aa = aa;
            this.ea = ea;
            this.aa.buttonListener(new ButtonEvent());
            this.ea.buttonListeners(new ButtonEvent());
            this.ap.buttonListener(new ButtonEvent());
            as = new AppointmentServiceImplementation(ap, aa, ea);
        }
        
        class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ap.srchbydt){
                as.searchByDate();
            }
            else if(e.getSource() == ap.addAppointmentButton){
//                aa.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                aa.setVisible(true);
            }
            else if(e.getSource() == ap.editAppointmentButton){
                as.editView();
            }
            else if(e.getSource() == ap.deleteAppointmentButton){
                as.delete();
            }
            else if(e.getSource() == aa.save){
                as.save();
            }
            else if(e.getSource() == ea.update){
                as.update();
            }
        }   
    }
}
