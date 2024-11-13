/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Student;

import Connection.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author estip
 */
public class StudentDAOImplementation implements StudentDAO{

    @Override
    public ResultSet fetchAll() {
        ResultSet rs = null;
        Connection conn = null;
        try {
                conn = Ticket .getConn();
                String sql = "SELECT s.stud_id as '#', " 
                        + "s.fname as 'First Name', " 
                        + "s.mname as 'Middle Name', " 
                        + "s.lname as 'Last Name', "
                        + "s.gender as 'Gender', "
                        + "s.section as 'Section', "
                        + "s.college as 'College', " 
                        + "s.program as 'Program', " 
                        + "s.barangay as 'Barangay', "
                        + "s.municipal as 'Municipality', "
                        + "s.guardian as 'Guardian', " 
                        + "s.guardian_no as 'Guardian No.', " 
                        + "CONCAT_WS(' ', c.fname, c.lname, CONCAT(' (', c.college, ') ', '(Secretary ID:', c.sec_id, ')')) AS 'Secretary' " 
                        + "FROM student s, secretary c " 
                        + "WHERE s.sec_id = c.sec_id";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

            return rs;
    }

    @Override
    public void save(StudentModel student) {
            Connection conn = null;
                    try {
                conn = Ticket . getConn();
                String sql = "Insert into student values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, student.getStud_id());
                stmt.setString(2, student.getFname());
                stmt.setString(3, student.getMname());
                stmt.setString(4, student.getLname());
                stmt.setString(5, student.getGender());
                stmt.setString(6, student.getSection());
                stmt.setString(7, student.getCollege());
                stmt.setString(8, student.getProgram());
                stmt.setString(9, student.getBarangay());
                stmt.setString(10,student.getMunicipal());
                stmt.setString(11,student.getGuardian());
                stmt.setString(12,student.getGuar_no());
                stmt.setString(13,student.getSec_id());
                  stmt.execute();         
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(StudentModel student) {
    Connection conn = null;
        try {
            conn = Ticket . getConn();
            String sql = "Update student set fname =?, mname =?, lname =?,"
                    + " section =?, college =?, program =?, gender =?,"
                    + "barangay =?, municipal =?,"
                    + "guardian =?, guardian_no =?,"
                    + "sec_id =?"
                    + "where stud_id ='"+student.getStud_id()+"'"; 
                        
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getFname());
            stmt.setString(2, student.getMname());
            stmt.setString(3, student.getLname());
            stmt.setString(4, student.getSection());
            stmt.setString(5, student.getCollege());
            stmt.setString(6, student.getProgram());
            stmt.setString(7, student.getGender());
            stmt.setString(8, student.getBarangay());
            stmt.setString(9,student.getMunicipal());
            stmt.setString(10,student.getGuardian());
            stmt.setString(11,student.getGuar_no());
            stmt.setString(12,student.getSec_id());
              stmt.execute();         
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String student_id) {
        Connection conn = null;
        try {
            conn = Ticket.getConn();
            String sql = "Delete from student where stud_id = '"+student_id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
