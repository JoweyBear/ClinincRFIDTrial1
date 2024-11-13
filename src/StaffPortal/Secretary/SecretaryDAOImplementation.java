/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Secretary;

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
 * @author richelle
 */
public class SecretaryDAOImplementation implements SecretaryDAO {

    @Override
    public ResultSet fetchAll() {
       ResultSet rs = null;
       Connection con = null;
            try {
           con = Ticket.getConn();
           String sql ="SELECT sec_id as 'Sectretary ID', fname as 'First Name',"
                   +"mname as 'Middle Name', lname as 'Last Name', college as 'College',"
                   +"number as'Mobile Number' from secretary";
           Statement stmt= con.createStatement();
        
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SecretaryDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;     
    }

    @Override
    public void save(SecretaryModel sec) {
           Connection con = null;
        try {
            con = Ticket.getConn();
            String sql = "Insert into secretary values (?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sec.getSecID());
            stmt.setString(2, sec.getFName());
            stmt.setString(3, sec.getMName());
            stmt.setString(4, sec.getLName());
            stmt.setString(5, sec.getCollege());
            stmt.setString(6, sec.getNumber());        
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Secretary Added!");
        } catch (SQLException ex) {
            Logger.getLogger(SecretaryDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        
     }

    @Override
    public void update(SecretaryModel sec) {
         Connection con = null;
        try {
            con = Ticket.getConn();
            String sql = "Update secretary set fname = ?, "
                    + "mname = ?, mname = ?,college = ? ,"
                    + "number = ? where sec_id = '"+sec.getSecID()+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sec.getFName());
            stmt.setString(2, sec.getMName());
            stmt.setString(3, sec.getLName());
            stmt.setString(4, sec.getCollege());
            stmt.setString(5, sec.getNumber());
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Scretary Information Updated!");
        } catch (SQLException ex) {
            Logger.getLogger(SecretaryDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void delete(String secID) {
       Connection con = null;
        try {
       con = Ticket.getConn();
       String sql = "DELETE FROM secretary WHERE sec_id = ?";
       PreparedStatement stmt = con.prepareStatement(sql);
       stmt.setString(1, secID);
        stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SecretaryDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
