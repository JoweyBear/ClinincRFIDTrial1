/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Appointment;

import StaffPortal.Appointment.Views.*;
import Util.QuickSearch;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.sql.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author joena & jheza
 */
public class AppointmentServiceImplementation implements AppointmentService
{
    AppointmentPanel ap;
    AddAppointment aa;
    EditAppointment ea;
    AppointmentDAO dao = new AppointmentDAOImplementation();
    
    public AppointmentServiceImplementation(AppointmentPanel ap, AddAppointment aa, EditAppointment ea){
       this.ap = ap;
       this.aa = aa;
       this.ea = ea;
       setTableData();
    }
    @Override
        public void setTableData() {
            ResultSet rs = dao.fetchAll();
            ap.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)){
                TableCellEditor tce = ap.jTable1.getDefaultEditor(c);
                if (tce instanceof DefaultCellEditor){
                    ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
                }   
            }
        new QuickSearch(ap, ap.jTable1, ap.srch, rs);   
        }
    @Override
    public void searchByDate() {
        LocalDate date1 = ap.datePicker1.getDate(); 
        LocalDate date2 = ap.datePicker2.getDate(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        String strDate1 = date1.format(formatter);
        String strDate2 = date2.format(formatter);
//        String strDate1 = ((JTextField)ap.datePicker1.getData().getUiComponent()).getText();
//        String strDate2 = ((JTextField)ap.datePicker2.getData().getUiComponent()).getText();
        ResultSet rs = dao.searchByDate(strDate1, strDate2);
        ap.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor tce = ap.jTable1.getDefaultEditor(c);
            if (tce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
            }
        new QuickSearch(ap, ap.jTable1, ap.srch, rs);   
        }
    }


    @Override
    public void save() {
        if(aa.student_id.getSelectedItem().equals("")
                ||aa.purpose.getText().equals("")
                ||aa.status.getSelectedItem().equals("")
                ||aa.staff_id.getSelectedItem().equals("")
                ||aa.timePicker1.getTime() == null
                ||aa.date.getDate() == null)               
               {
            JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else {
            AppointmentModel appointmentModel = new AppointmentModel();
            String id = aa.student_id.getSelectedItem().toString();
            int origin = id.indexOf(":")+1;
            String student_id =(id.substring(origin,(id.length()-1)));
            appointmentModel.setStud_id(student_id);
            appointmentModel.setStatus(aa.status.getSelectedItem().toString());
            String staffid = aa.staff_id.getSelectedItem().toString();
            int originstaff = staffid.indexOf(":")+1;
            String staff_id =(staffid.substring(originstaff,(staffid.length()-1)));
            appointmentModel.setStaff_id(staff_id);
            appointmentModel.setPurpose(aa.purpose.getText());
            java.util.Date utilDate = aa.date.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            appointmentModel.setDate(sqlDate);
            LocalTime localTime = aa.timePicker1.getTime();
            java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);  
            appointmentModel.setTime(sqlTime);
            dao.save(appointmentModel);
            JOptionPane.showMessageDialog(null,"Appointment Added!");
            setTableData();

        }
    }

    @Override
    public void editView() {
        int dataRow = ap.jTable1.getSelectedRow();
        if(dataRow >= 0)
        {
                int appointment_id = Integer.parseInt(ap.jTable1.getValueAt(dataRow,0) + "");
                ea.student_id.setSelectedItem((String) ap.jTable1.getValueAt(dataRow,1));
                ea.purpose.setText((String) ap.jTable1.getValueAt(dataRow,3));
                ea.status.setSelectedItem((String) ap.jTable1.getValueAt(dataRow,4));
                ea.date.setDate((Date) ap.jTable1.getValueAt(dataRow, 5));
                java.sql.Time sqlTime = (java.sql.Time) ap.jTable1.getValueAt(dataRow,6);
                LocalTime localTime = sqlTime.toLocalTime(); // Conversion from java.sql.Time to java.time.LocalTime
                ea.timePicker1.setTime(localTime );
                ea.staff_id.setSelectedItem((String) ap.jTable1.getValueAt(dataRow,9));
                ea.setTitle("Edit Information of Appointment ID: " + appointment_id);
                ea.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                ea.setVisible(true);
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select Appointment to edit.");
        }
    }

    @Override
    public void update() {
        if(ea.student_id.getSelectedItem().equals("")
                ||ea.purpose.getText().equals("")
                ||ea.status.getSelectedItem().equals("")
                ||ea.staff_id.getSelectedItem().equals("")
                ||ea.timePicker1.getTime() == null
                ||ea.date.getDate() == null)
                
               {
            JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            AppointmentModel appointmentModel = new AppointmentModel();
            int dataRow = ap.jTable1.getSelectedRow();
            appointmentModel.setAppt_id(Integer.parseInt(ap.jTable1.getValueAt(dataRow,0).toString()));
            String id = ea.student_id.getSelectedItem().toString();
            int origin = id.indexOf(":")+1;
            String stud_id =(id.substring(origin,(id.length())));
            appointmentModel.setStud_id(stud_id);
            System.out.println(stud_id);
            appointmentModel.setPurpose(ea.purpose.getText());
            String staffid = ea.staff_id.getSelectedItem().toString();
            int originstaff = staffid.indexOf(":")+1;
            String staff_id =(staffid.substring(originstaff,(staffid.length()-1)));
            appointmentModel.setStaff_id(staff_id);
            appointmentModel.setStatus((String) ea.status.getSelectedItem());
            java.util.Date utilDate = ea.date.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            appointmentModel.setDate(sqlDate);
            LocalTime localTime = ea.timePicker1.getTime();
            java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);  
            appointmentModel.setTime(sqlTime);
            dao.update(appointmentModel);
            JOptionPane.showMessageDialog(null,"Appointment Updated!");
            setTableData();
        }

    }

    @Override
    public void delete() {
        int dataRow = ap.jTable1.getSelectedRow();
        if(dataRow >= 0)
        {
            int appt_id = Integer.valueOf(ap.jTable1.getValueAt(dataRow,0).toString());
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                               + "Delete Appointment: " + appt_id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                dao.delete(appt_id);
               setTableData(); 
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select Appointment to delete.");
    }
        }
    }


    
}
