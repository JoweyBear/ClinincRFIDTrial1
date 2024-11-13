/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Appointment;

import Connection.Ticket;
import StaffPortal.Secretary.SecretaryModel;
import StaffPortal.Student.StudentModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class AppointmentDAOImplementation implements AppointmentDAO {

    @Override
    public ResultSet fetchAll(String student_id) {
        ResultSet rs = null;
        Connection conn = null;
        try {
        conn = Ticket.getConn();
        
        System.out.println(student_id);
        String sql = "SELECT a.appt_id AS 'Appointment #', a.stud_id AS 'Student ID', "
                        + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
                        + "a.purpose AS 'Purpose', a.status AS 'Status', a.date AS 'Appointment Date', "
                        + "a.time AS 'Appointment Time', s.college AS 'College', s.program AS 'Program', "
                        + "CONCAT_WS(' ', c.fname, c.lname, CONCAT('(Staff ID:', a.staff_id, ')')) AS 'Staff Name' "
                        + "FROM appointment a "
                        + "JOIN student s ON a.stud_id = s.stud_id "
                        + "JOIN staff c ON a.staff_id = c.staff_id "
                        + "WHERE a.status = 'Appointment' AND a.stud_id = '"+student_id+"' ";
//                        + "ORDER BY a.appt_id";


        
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

//    @Override
//    public StudentModel fetchStudentInfo(String student_id) {
//        Connection conn = Ticket.getConn();
//        ResultSet rs;
//        StudentModel student = new StudentModel();
//        try 
//        {
//            String sql = "SELECT stud_id, fname, mname, lname, section, college, program, guardian, guardian_no, sec_id "
//                        + "FROM student WHERE stud_id = '" + student_id + "'";
//            
//            Statement stmt = conn.createStatement();
//            rs = stmt.executeQuery(sql);
//            if (rs.next()) 
//            {
//                student.setFname(rs.getString("fname"));
//                student.setLname(rs.getString("lname"));
//                student.setGuardian(rs.getString("guardian"));
//                student.setGuar_no(rs.getString("guardian_no"));
//                student.setSec_id(rs.getString("sec_id"));
//            }
//        } catch (SQLException err) 
//        {
//            JOptionPane.showMessageDialog(null, err.getMessage());
//            student = null;
//        }
//        return student;    
//    }


    
}
