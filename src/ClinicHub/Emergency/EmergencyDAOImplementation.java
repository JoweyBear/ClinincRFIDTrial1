/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Emergency;

import StaffPortal.Appointment.AppointmentModel;
import Connection.Ticket;
import StaffPortal.Secretary.SecretaryModel;
import Util.GlobalVar;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class EmergencyDAOImplementation implements EmergencyDAO{

    @Override
    public SecretaryModel fetchSecretaryInfo(String sec_id) {
        Connection conn = Ticket.getConn();
        ResultSet rs;
        SecretaryModel sec = new SecretaryModel();
        System.out.println(sec_id);
        try 
        {
            String sql = "SELECT fname, lname, number " 
                   + "FROM secretary WHERE sec_id = '" + sec_id + "'";

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) 
            {
                sec.setFName(rs.getString("fname"));
                sec.setLName(rs.getString("lname"));
                sec.setNumber(rs.getString("number"));
                System.out.println(sec.getNumber());
            }
        } catch (SQLException err) 
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
            sec = null;
        }
        return sec;    
    }    


    @Override
    public void save(AppointmentModel appt) {
        Connection conn = null;
        try {    
            conn = Ticket.getConn();
            String sql = "Insert into appointment values (0,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, GlobalVar.activeStudent.getStud_id());
            stmt.setString(2, appt.getPurpose());
            stmt.setString(3, appt.getStatus());
            stmt.setString(4, appt.getStaff_id());
            stmt.setDate(5, (Date) appt.getDate());
            stmt.setTime(6, appt.getTime());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmergencyDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
