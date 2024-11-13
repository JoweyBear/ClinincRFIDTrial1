/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.AnnualReport;

import Connection.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joena
 */
public class ReportAnnualDAOImplementation implements ReportAnnualDAO {

    @Override
    public Map<String, Map<String, Integer>> displayCharts(int year) {
        Map<String, Map<String, Integer>> purposeCountMap = new HashMap<>();
    
        String sql = "SELECT a.status, s.college, a.purpose, COUNT(*) AS count " +
                     "FROM appointment a " +
                     "JOIN student s ON a.stud_id = s.stud_id " +
                     "WHERE YEAR(a.date) = ? " +
                     "GROUP BY a.status, s.college, a.purpose";

        try (Connection conn = Ticket.getConn(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
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
    
//    public static void main(String args[]){
//        int year = (2023);
//        String sql = "SELECT a.status, s.college, a.purpose, a.date " +
//             "FROM appointment a " +
//             "JOIN student s ON a.stud_id = s.stud_id " +
//             "WHERE YEAR(a.date) = ?";
//            
//            try (Connection conn = Ticket.getConn(); 
//            PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, year);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                String college = rs.getString("college");
//                String status = rs.getString("status");
//                String purpose = rs.getString("purpose");
//                Date date = rs.getDate("date");
//
//                // Print out the raw data for inspection
//                System.out.println("College: " + college + ", Status: " + status + ", Purpose: " + purpose + ", Date: " + date);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();  // Log any SQL exceptions
//        }
//    }
}
