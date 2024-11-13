/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Reports;

/**
 *
 * @author joena
 */
public class ReportSummaryController {
    ReportSummaryPanel rsp;
    ReportSumService rss;
    
    public ReportSummaryController(ReportSummaryPanel rsp){
        this.rsp = rsp;
        rss = new ReportSumServiceImplementation(rsp);    
    }
}
