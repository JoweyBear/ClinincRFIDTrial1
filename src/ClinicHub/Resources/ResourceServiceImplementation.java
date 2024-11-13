/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Resources;

import StaffPortal.Appointment.AppointmentModel;
import Util.GlobalVar;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author joena
 */
public class ResourceServiceImplementation implements ResourcesService {
    ResourcesFrame rf;
    ResourcesDAO dao = new ResourcesDAOImplementation();
    
    public ResourceServiceImplementation(ResourcesFrame rf){
        this.rf = rf;
        setLabels();
        
    }
    @Override
    public void save() {
        AppointmentModel appt = new AppointmentModel();
        String status = "Resources";
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Date sqlDate = Date.valueOf(today);
        Time sqlTime = Time.valueOf(now);        
        appt.setStud_id(GlobalVar.activeStudent.getStud_id());
        appt.setPurpose(rf.prps.getText());
        appt.setStatus(status);
        String staffid = rf.staff_id.getSelectedItem().toString();
        int originstaff = staffid.indexOf(":")+1;
        String staff_id =(staffid.substring(originstaff,(staffid.length()-1)));
        appt.setStaff_id(staff_id);
        appt.setDate(sqlDate);
        appt.setTime(sqlTime);
        dao.save(appt);
        System.out.println("Resources Given Recorded.");
        
    }
    
    public void setLabels(){
        rf.stdd.setText(GlobalVar.activeStudent.getStud_id());
        rf.stdnm.setText(GlobalVar.activeStudent.getFname()+ GlobalVar.activeStudent.getLname());
    }   
}
