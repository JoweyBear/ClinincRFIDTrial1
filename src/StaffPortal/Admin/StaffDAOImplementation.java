/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Admin;

import Connection.Ticket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author joena
 */
public class StaffDAOImplementation implements StaffDAO {

    @Override
    public ResultSet fetchAll() {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Ticket.getConn();
            String sql = "SELECT staff_id as '#', fname as 'First Name', "
                         + "mname as 'Middle Name', lname as 'Last Name',"
                         + "position as 'Position', username as 'Username' from staff";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
            
        return rs;
    }

    @Override
    public void save(StaffModel staff) {
        Connection conn = null;
            try {
            conn = Ticket.getConn();
            String sql = "Insert into staff values (?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql); 
            stmt.setString(1, staff.getStaff_id());
            stmt.setString(2, staff.getStFname());
            stmt.setString(3, staff.getStMname());
            stmt.setString(4, staff.getStLname());
            stmt.setString(5, staff.getPosition());
            stmt.setString(6, staff.getUsername());
            stmt.setString(7, staff.getPass());
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Staff Added!");
        }catch (SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(null, "Duplicate Username");
        }catch (SQLException ex) {
            Logger.getLogger(StaffDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

    @Override
    public void update(StaffModel staff) {
        Connection conn = null;
            try {
            conn = Ticket.getConn();
            String sql = "Update staff set fname = ?, "
                    + "mname = ?, lname = ?, position = ?, "
                    + "username = ?, hash = ?"
                    + "where staff_id = '"+staff.getStaff_id()+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, staff.getStFname());
            stmt.setString(2, staff.getStMname());
            stmt.setString(3, staff.getStLname());
            stmt.setString(4, staff.getPosition());
            stmt.setString(5, staff.getUsername());
            stmt.setString(6, staff.getPass());
//            System.out.println(staff.getUsername());
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Staff Information Updated!");
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);

        }

            }

    @Override
    public void delete(String staff_id) {
        Connection conn = null;
            try {
            conn = Ticket.getConn();
            String sql = "Delete from staff where staff_id = '"+staff_id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Staff Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }

            }         

}
