/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Reports;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author joena
 */
public class ReportSumServiceImplementation implements ReportSumService {
    
    ReportSummaryPanel rsp;
    ReportSummaryDAO dao = new ReportSumDAOImplementation();
    public ReportSumServiceImplementation(ReportSummaryPanel rsp){
        this.rsp = rsp;
        
        setDataTable();
        setDataTextArea();
    }

    private void setDataTable() {
            ResultSet rs = dao.fetchAll();
            rsp.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)){
                TableCellEditor tce = rsp.jTable1.getDefaultEditor(c);
                if(tce instanceof DefaultCellEditor){
                    ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
                }   
            }    
    }

    private void setDataTextArea() {
            Map<String, Integer> purposeCountMap = dao.statusCount();
            int totalVisits = purposeCountMap.values().stream().mapToInt(Integer::intValue).sum();
            rsp.jTextArea1.append("Visit Summary Report\n");
            rsp.jTextArea1.append("====================\n");
            rsp.jTextArea1.append("Total Visits: " + totalVisits + "\n\n");
            System.out.println(totalVisits);

            for (Map.Entry<String, Integer> entry : purposeCountMap.entrySet()) {
                String purpose = entry.getKey();
                int count = entry.getValue();
                double percentage = (count / (double) totalVisits) * 100;
                rsp.jTextArea1.append("Purpose: " + purpose + "\n");
                rsp.jTextArea1.append("Count: " + count + "\n");
                rsp.jTextArea1.append("Percentage: " + String.format("%.2f", percentage) + "%\n\n");
            }    
    }
    
}
