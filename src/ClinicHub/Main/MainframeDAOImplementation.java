/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Main;

import Connection.Ticket;
import StaffPortal.Student.StudentModel;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class MainframeDAOImplementation implements MainframeDAO {

    @Override
    public boolean RFIDMatch(String stud_id) {
        boolean status = false;

    try (Connection conn = Ticket.getConn();
         PreparedStatement stmt = conn.prepareStatement("SELECT stud_id FROM student WHERE stud_id = ?")) {
        
        stmt.setString(1, stud_id);  
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                status = true;
            }
        }
        
    } catch (SQLException err) {
        JOptionPane.showMessageDialog(null, "Error: " + err.getMessage());
    }
    
    return status;
}

    @Override
    public StudentModel getStudentInformation(String stud_id) {
        StudentModel stdnt = new StudentModel();

        String sql = "SELECT s.stud_id, s.fname, s.lname, s.section, s.guardian, s.guardian_no, s.sec_id, "  // Comma added here
                   + "sec.college, sec.number AS sec_mobile "
                   + "FROM student s "
                   + "JOIN secretary sec ON s.sec_id = sec.sec_id "
                   + "WHERE s.stud_id = ?";

        try (Connection conn = Ticket.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stud_id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stdnt.setStud_id(rs.getString("stud_id"));
                    stdnt.setFname(rs.getString("fname"));
                    stdnt.setLname(rs.getString("lname"));
                    stdnt.setCollege(rs.getString("college"));
                    stdnt.setSection(rs.getString("section"));
                    stdnt.setGuardian(rs.getString("guardian"));
                    stdnt.setGuar_no(rs.getString("guardian_no"));
                    stdnt.setSec_id(rs.getString("sec_id"));
                    stdnt.setSec_no(rs.getString("sec_mobile"));
                } else {
                    stdnt = null;  // Set to null if no matching student is found
                }
            }

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error: " + err.getMessage());
            stdnt = null;
        }

        return stdnt;
    }
//        @Override
//    public void save(AppointmentModel appt) {
//        Connection conn = null;
//        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
//        try {
//            conn = Ticket.getConn();
////            String sql = "Insert into appointment (stud_id,admin_id,logtime,status) values (?,?,?,?)";
//            String sql = "Insert into appointment values (0,?,?,?,?,?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, appt.getStud_id());
//            stmt.setString(2, appt.getPurpose());
//            stmt.setString(3, appt.getStatus());
//            stmt.setString(4, Globals.loggedInStaff.getStaff_id());
//            stmt.setTimestamp(5, date);
//            stmt.execute();
//        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(RFIDDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Student not found in database");
//        } catch (SQLException Sex) {
//            Logger.getLogger(RFIDDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    }
 
    
}
