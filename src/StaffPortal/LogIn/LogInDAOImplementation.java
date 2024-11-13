/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.LogIn;

import StaffPortal.Admin.StaffModel;
import Connection.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class LogInDAOImplementation implements LogInDAO{

    @Override
    public StaffModel staffLogin(String user, String pass) {
        Connection conn = Ticket.getConn();
        ResultSet rs;
        StaffModel staff = new StaffModel();
        try{
            if (user != null && user != null){
                String sql = "SELECT * FROM staff "
                            + "where username ='" + user + "' and hash='" + pass + "'";
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next()){
                    //in this case enter when at least one result comes it means user is valid
                    staff.setStaff_id(rs.getString("staff_id"));
                    staff.setStFname(rs.getString("fname"));
                    staff.setStMname(rs.getString("mname"));
                    staff.setStLname(rs.getString("lname"));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong username or password!","Error",JOptionPane.ERROR_MESSAGE);
                    staff = null;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return staff;
    }
}

