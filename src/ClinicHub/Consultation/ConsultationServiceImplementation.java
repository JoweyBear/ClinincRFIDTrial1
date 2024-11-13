/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Consultation;

import StaffPortal.Appointment.AppointmentModel;
import SMS.SMSService;
import SMS.SMSServiceImplementation;
import StaffPortal.Secretary.SecretaryModel;
import Util.GlobalVar;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author joena
 */
public class ConsultationServiceImplementation implements ConsultationService{
    ConsultationFrame cf;
    ConsultationDAO dao = new ConsultationDAOImplementation();
    
    public ConsultationServiceImplementation(ConsultationFrame cf){
        this.cf = cf;
        setLabels();
    }
    @Override
    public void SMS() {
        String stud_id = GlobalVar.activeStudent.getStud_id();
        String sec_id = GlobalVar.activeStudent.getSec_id();
        SecretaryModel sec = dao.fetchSecretaryInfo(sec_id);
//            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss");
            java.util.Date date = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            String time = tf.format(date);

//            String time = tf.format(now);
            String name = GlobalVar.activeStudent.getFname() + GlobalVar.activeStudent.getLname();
            String college = GlobalVar.activeStudent.getCollege();
            String section = GlobalVar.activeStudent.getSection();
            String visitPurpose = cf.prps.getText();
            String secmobile = sec.getNumber();
            
            String message = "Clinic Alert:" +name+",(" +stud_id+"),"+college+","+section+
                    " - Consultation: " +visitPurpose+" at "+time+". ";
            
            SMSService smsService =  new SMSServiceImplementation();
            smsService.sendSMS(secmobile, message);
            System.out.println("Message sent to: " + secmobile);
}

    @Override
    public void save() {
        AppointmentModel appt = new AppointmentModel();
        String status = "Consultation";
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Date sqlDate = Date.valueOf(today);
        Time sqlTime = Time.valueOf(now);        
        appt.setStud_id(GlobalVar.activeStudent.getStud_id());
        appt.setPurpose(cf.prps.getText());
        String staffid = cf.staff_id.getSelectedItem().toString();
        int originstaff = staffid.indexOf(":")+1;
        String staff_id =(staffid.substring(originstaff,(staffid.length()-1)));
        appt.setStaff_id(staff_id);
        appt.setStatus(status);
        appt.setDate(sqlDate);
        appt.setTime(sqlTime);
        dao.save(appt);
        System.out.println("Consultation Recorded.");
        
    }
    
    public void setLabels(){
        cf.stdd.setText(GlobalVar.activeStudent.getStud_id());
        cf.stdnm.setText(GlobalVar.activeStudent.getFname()+ GlobalVar.activeStudent.getLname());
    }
}
