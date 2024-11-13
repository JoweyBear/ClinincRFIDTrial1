/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.ReportMonthly;

import Connection.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joena
 */
public class ReportMonthlyDAOImplementation implements ReportMonthlyDAO {

    @Override
    public Map<String, Map<String, Integer>> displayCharts(int month) {
    Map<String, Map<String, Integer>> purposeCountMap = new HashMap<>();
    
    String sql = "SELECT a.status, s.college, a.purpose, COUNT(*) AS count " +
                 "FROM appointment a, student s " +
                 "WHERE a.stud_id = s.stud_id " +
                 "AND MONTH(a.date) = ? " +
                 "AND YEAR(a.date) = YEAR(CURRENT_DATE) " +
                 "GROUP BY a.status, s.college, a.purpose";
    
    try (Connection conn = Ticket.getConn(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, month);
        ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                String college = rs.getString("college");
                String status = rs.getString("status");
                int count = rs.getInt("count");

                // Use computeIfAbsent to simplify map logic
                purposeCountMap.computeIfAbsent(college, k -> new HashMap<>())
                               .merge(status, count, Integer::sum);

                // Debugging output to verify data
                System.out.println("Status: " + status + ", College: " + college + ", Count: " + count);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();  // Logging error for debugging
    }
    
    return purposeCountMap;
}
//    public Map<String, Integer>displayCharts(int month) {
//        ResultSet rs = null;
//        Connection conn = null;
//        Map<String, Integer> purposeCountMap = new HashMap<>();
//        try {
//            conn = Ticket.getConn();
//
//
//            String sql = "SELECT a.status, s.college, a.purpose, COUNT(*) AS count " +
//                         "FROM appointment a, student s " +
//                         "WHERE a.stud_id = s.stud_id " +
//                         "AND MONTH(a.date) = ? " +
//                         "AND YEAR(a.date) = YEAR(CURRENT_DATE) " +
//                         "GROUP BY a.status, s.college, a.purpose";
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, month); 
//
//            rs = stmt.executeQuery(); 
//
//            while (rs.next()) {
//                String status = rs.getString("status");
//                String college = rs.getString("college");
//                String purpose = rs.getString("purpose");
//                int count = rs.getInt("count");
//                purposeCountMap.put(status, count);
//
//                // Process the results as needed
//                System.out.println("Purpose: " + purpose + ", Department: " + college + ", Count: " + count);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ReportMonthlyDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            return purposeCountMap;
//            }

    @Override
    public Map<String, Integer>displayCurrentMonth() {
            ResultSet rs = null;
            Connection conn = null;
            Map<String, Integer> purposeCountMap = new HashMap<>();

            try {
                conn = Ticket.getConn();
                String sql = "SELECT a.status, s.college, a.purpose, COUNT(*) AS count FROM appointment a, student s " +
                             "WHERE a.stud_id = s.stud_id AND MONTH(a.date) = MONTH(CURRENT_DATE) " +
                             "AND YEAR(a.date) = YEAR(CURRENT_DATE) " +
                             "GROUP BY a.status, s.college, a.purpose";

                PreparedStatement stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(); 

                // Process the results and store in the map
                while (rs.next()) {
                    String status = rs.getString("status");
                    int count = rs.getInt("count");
                    purposeCountMap.put(status, count);
                }
        } catch (SQLException ex) {
            Logger.getLogger(ReportMonthlyDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
            return purposeCountMap;
    }
        
            
    
}
