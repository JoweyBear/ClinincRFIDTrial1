/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Appointment;

import StaffPortal.Secretary.SecretaryModel;
import StaffPortal.Student.StudentModel;
import SMS.SMSService;
import SMS.SMSServiceImplementation;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import Util.GlobalVar;
import java.awt.Dialog;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author joena
 */
public class AppointmentServiceImplementation implements AppointmentService {
    AppointmentPanel ap;
    AppointmentDAO dao = new AppointmentDAOImplementation();
    
    public AppointmentServiceImplementation(AppointmentPanel ap){
        this.ap = ap;
        
        setTableData();
    }
    
    @Override
    public void setTableData() {
            String student_id = GlobalVar.activeStudent.getStud_id();
            System.out.println("student id:" + GlobalVar.activeStudent.getStud_id());
            ResultSet rs = dao.fetchAll(student_id);
            ap.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)){
                TableCellEditor tce = ap.jTable1.getDefaultEditor(c);
                if(tce instanceof DefaultCellEditor){
                    ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
                }   
            }
    }

    @Override
    public void SMS() {
        int dataRow = ap.jTable1.getSelectedRow();
        if (dataRow >= 0) {
            int appointment_id = Integer.parseInt(ap.jTable1.getValueAt(dataRow, 0) + "");
            String student_id = GlobalVar.activeStudent.getStud_id();
            String sec_id = GlobalVar.activeStudent.getSec_id();

            SecretaryModel sec = dao.fetchSecretaryInfo(sec_id);

            LocalDateTime now = LocalDateTime.now();
            SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss");
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            String time = tf.format(date);

            String name = (String) ap.jTable1.getValueAt(dataRow, 2);
            String mobile = sec.getNumber();
            String college = GlobalVar.activeStudent.getCollege();
            String section = GlobalVar.activeStudent.getSection();
            String visitPurpose = (String) ap.jTable1.getValueAt(dataRow, 3);

            String message = "Clinic Alert: " + name + ",("  + student_id + ")" + college + ", " + section + 
                             " - Scheduled Appointment for " + visitPurpose + " at " + time + ".";
//            String message = "Clinic Alert: ID:2021-POB-0143,Joenavic Bacting,CICT,IV-B - Scheduled Appointment for Eye Check-up at 10:31:20 PM.";
//            String message = "Clinic Alert.. 123456789012345678901234567890123456789012345678901234567890";
            SMSService smsService = new SMSServiceImplementation();
//                String message = "Clinic Alert: Student/Patient Name: '" + name + "' ID: '" 
//                 + "' College/Section: '"
//                 + "' Visit Type:  Purpose of Visit: '" 
//                 + "' Time of Visit: '"  "'.";        // Send SMS to the secretary
            smsService.sendSMS(mobile, message);

            // Debugging output for console
            System.out.println("Message sent to: " + mobile);
        } else {
            System.out.println("No appointment selected.");
        }    
    }
    
}
