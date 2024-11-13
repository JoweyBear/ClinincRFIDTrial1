/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Emergency;

import StaffPortal.Appointment.AppointmentModel;
import SMS.*;
import StaffPortal.Secretary.SecretaryModel;
import Util.GlobalVar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author joena
 */
public class EmergencyServiceImplementation implements EmergencyService {
    EmergencyFrame ef;
    EmergencyDAO dao = new EmergencyDAOImplementation();
    
    public EmergencyServiceImplementation(EmergencyFrame ef){
        this.ef = ef;
        setLabels();
    }
    
    
    @Override
    public void SMSHome() {
        String stud_id = GlobalVar.activeStudent.getStud_id();
        String sec_id = GlobalVar.activeStudent.getSec_id();
        SecretaryModel sec = dao.fetchSecretaryInfo(sec_id);
//            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String time = now.format(formatter);
//            String time = tf.format(now);
            String name = GlobalVar.activeStudent.getFname() + GlobalVar.activeStudent.getLname();
            String college = GlobalVar.activeStudent.getCollege();
            String section = GlobalVar.activeStudent.getSection();
            String visitPurpose = ef.prps.getText();
            String secmobile = sec.getNumber();
            String gaur_no = GlobalVar.activeStudent.getGuar_no();
            
            String message = "Clinic Alert:" +name+",(" +stud_id+"),"+college+","+section+
                    " - Emergency: " +visitPurpose+" at "+time+". Will be sent home.";
            
            SMSService smsService =  new SMSServiceImplementation();
            smsService.sendSMS(secmobile, message);
            smsService.sendSMS(gaur_no, message);
            System.out.println("Message sent to: " + secmobile + gaur_no);
    }

    @Override
    public void SMSHospital() {
        String stud_id = GlobalVar.activeStudent.getStud_id();
        String sec_id = GlobalVar.activeStudent.getSec_id();
        SecretaryModel sec = dao.fetchSecretaryInfo(sec_id);
//            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss a");
//            LocalDateTime now = LocalDateTime.now();
//            String time = tf.format(now);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String time = now.format(formatter);
            String name = GlobalVar.activeStudent.getFname() + GlobalVar.activeStudent.getLname();
            String college = GlobalVar.activeStudent.getCollege();
            String section = GlobalVar.activeStudent.getSection();
            String visitPurpose = ef.prps.getText();
            String secmobile = sec.getNumber();
            String gaur_no = GlobalVar.activeStudent.getGuar_no();
            
            String message = "Clinic Alert:" +name+",(" +stud_id+"),"+college+","+section+
                    " - Emergency: " +visitPurpose+" at "+time+". Will be sent to hospital.";
            
            SMSService smsService =  new SMSServiceImplementation();
            smsService.sendSMS(secmobile, message);
            smsService.sendSMS(gaur_no, message);
            System.out.println("Message sent to: " + secmobile + gaur_no);
    }

    @Override
    public void save() {
        AppointmentModel appt = new AppointmentModel();
        String status = "Emergency";
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Date sqlDate = Date.valueOf(today);
        Time sqlTime = Time.valueOf(now);        
        appt.setStud_id(GlobalVar.activeStudent.getStud_id());
        appt.setPurpose(ef.prps.getText());
        appt.setStatus(status);
        String staffid = ef.stff.getSelectedItem().toString();
        int originstaff = staffid.indexOf(":")+1;
        String staff_id =(staffid.substring(originstaff,(staffid.length()-1)));
        appt.setStaff_id(staff_id);
        appt.setDate(sqlDate);
        appt.setTime(sqlTime);
        dao.save(appt);
        System.out.println("Emergency Recorded.");
        
    }
    
    public void setLabels(){
        ef.stdd.setText(GlobalVar.activeStudent.getStud_id());
        ef.stdnm.setText(GlobalVar.activeStudent.getFname()+ GlobalVar.activeStudent.getLname());
    }
}
