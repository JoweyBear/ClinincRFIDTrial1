/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.AnnualReport;

import java.util.Map;

/**
 *
 * @author joena
 */
public interface ReportAnnualDAO {
        Map<String, Map<String, Integer>>displayCharts(int year);
}
