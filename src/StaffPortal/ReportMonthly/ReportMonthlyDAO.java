/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.ReportMonthly;

import java.util.Map;
import java.sql.*;

/**
 *
 * @author joena
 */
public interface ReportMonthlyDAO {
    Map<String, Map<String, Integer>>displayCharts(int month);
    Map<String, Integer>displayCurrentMonth();
}
