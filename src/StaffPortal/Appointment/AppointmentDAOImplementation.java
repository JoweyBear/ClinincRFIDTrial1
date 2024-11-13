/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Appointment;

import Connection.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author joena & jheza
 */
public class AppointmentDAOImplementation implements AppointmentDAO
{

//    ResultSet rs = null;
//    Connection conn = null;
    
    
    @Override
    public ResultSet fetchAll() {
        ResultSet rs = null;
        Connection conn = null;
        try {
        conn = Ticket.getConn();
        
        
            String sql = "SELECT a.appt_id AS 'Appointment #', a.stud_id AS 'Student ID', "
                            + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
                            + "a.purpose AS 'Purpose', a.status AS 'Status', a.date AS 'Appointment Date', "
                            + "a.time AS 'Appointment Time', s.college AS 'College', s.program AS 'Program', "
                            + "CONCAT_WS(' ', c.fname, c.lname, CONCAT('(Staff ID:', a.staff_id, ')')) AS 'Staff Name' "
                            + "FROM appointment a "
                            + "JOIN student s ON a.stud_id = s.stud_id "
                            + "JOIN staff c ON a.staff_id = c.staff_id "
                            + "WHERE a.status = 'Appointment' "
                            + "ORDER BY a.appt_id";

        
//        String sql = "SELECT a.appt_id AS 'Appointment #', a.stud_id AS 'Student ID', "
//                        + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
//                        + "a.purpose AS 'Purpose', a.status AS 'Status', a.date AS 'Appointment Date', "
//                        + "a.time AS 'Appointment Time', s.college AS 'College', s.program AS 'Program' "
//                        + "FROM appointment a, student s WHERE a.stud_id = s.stud_id ORDER BY a.appt_id";

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
          return rs;      
    }

    @Override
    public ResultSet searchByDate(String strDate1, String strDate2) {
        ResultSet rs = null;
        Connection conn = null;
        try {
        conn = Ticket.getConn();
        
        
          String sql = "SELECT a.appt_id as 'Appointment #', "
                        + "a.stud_id as 'Student ID', "
                        + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
                        + "a.purpose as 'Purpose', "
                        + "a.status as 'Status', "
                        + "a.date as 'Appointment Date', "
                        + "a.time as 'Appointment Time', "
                        + "CONCAT_WS(' ', c.fname, c.lname, CONCAT('(Staff ID:', a.staff_id, ')')) AS 'Staff Name' "
                        + "FROM appointment a "
                        + "JOIN student s ON a.stud_id = s.stud_id "
                        + "JOIN staff c ON a.staff_id = c.staff_id "
                        + "WHERE a.status = 'Appointment' "
                        + "AND a.date BETWEEN '" + strDate1 + "' AND '" + strDate2 + "'";

        
//        String sql = "SELECT a.appt_id as 'Appointment #', "
//                    + "a.stud_id as 'Student ID', "
//                    + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
//                    + "a.purpose as 'Purpose', "
//                    + "a.status as 'Status', "
//                    + "a.date as 'Appointment Date', "
//                    + "a.time as 'Appointment Time' "
//                    + "FROM appointment a "
//                    + "JOIN student s ON a.stud_id = s.stud_id "
//                    + "WHERE a.date BETWEEN '"+strDate1+"' AND '"+strDate2+"'";

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }

    @Override
    public void save(AppointmentModel appmt) {
    try {
        Connection conn = null;
        
        conn = Ticket.getConn();
        String sql = "INSERT INTO appointment (stud_id, purpose, status, staff_id, date, time) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,appmt.getStud_id());
        stmt.setString(2,appmt.getPurpose());
        stmt.setString(3,appmt.getStatus());
        stmt.setString(4,appmt.getStaff_id());
        stmt.setDate(5, (Date) appmt.getDate());
        stmt.setTime(6,appmt.getTime());
        stmt.execute();
        conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
                   ex.printStackTrace();
        }
            }

    @Override
    public void update(AppointmentModel appmt) {
        try {
        Connection conn = null;
        
        conn = Ticket.getConn();
        String sql = "Update appointment set stud_id = ? , purpose = ?, "
                    + "status = ?, staff_id = ?, date = ?, time = ?"
                    + "where appt_id = '"+appmt.getAppt_id()+"'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,appmt.getStud_id());
        stmt.setString(2,appmt.getPurpose());
        stmt.setString(3,appmt.getStatus());
        stmt.setString(4,appmt.getStaff_id());
        stmt.setDate(5, (Date) appmt.getDate());
        stmt.setTime(6,appmt.getTime());
        stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int appointment_id) {
        try {
            Connection conn = Ticket.getConn();
            String sql = "Delete from Appointment where appt_id = ?";
            PreparedStatement stmt = conn.prepareStatement (sql);
            stmt.setInt(1, appointment_id);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
