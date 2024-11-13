/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Reports;

import StaffPortal.Appointment.AppointmentDAOImplementation;
import Connection.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author joena
 */
public class ReportSumDAOImplementation implements ReportSummaryDAO {

    @Override
    public ResultSet fetchAll() {
        ResultSet rs = null;
        Connection conn = null;
        try {
        conn = Ticket.getConn();
        
        
        String sql = "SELECT a.appt_id AS 'Appointment #', a.stud_id AS 'Student ID', "
                        + "CONCAT_WS(' ', s.fname, s.lname) AS 'Student Name', "
                        + "a.purpose AS 'Purpose', a.status AS 'Status', a.date AS 'Appointment Date', "
                        + "a.time AS 'Appointment Time', s.college AS 'College', s.program AS 'Program' "
                        + "FROM appointment a, student s WHERE a.stud_id = s.stud_id ORDER BY a.appt_id";

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
    public Map<String, Integer> statusCount() {
    Map<String, Integer> purposeCountMap = new HashMap<>();
    ResultSet rs = null;
    Connection conn = null;
    try {
        // Always create a fresh connection
        conn = Ticket.getConn(); // Ensure this gets a new connection each time
         
        String sql = "SELECT status, COUNT(*) AS count FROM appointment GROUP BY status";
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String purpose = rs.getString("status");
            int count = rs.getInt("count");
            purposeCountMap.put(purpose, count);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReportSumDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex);
    } finally {
        // Always close resources to avoid memory leaks
        try {
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return purposeCountMap;
}

}
