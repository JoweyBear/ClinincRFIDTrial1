/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.Reports;
import java.sql.ResultSet;
import java.util.Map;
/**
 *
 * @author joena
 */
public interface ReportSummaryDAO {
    ResultSet fetchAll();
    Map<String, Integer> statusCount();
}
