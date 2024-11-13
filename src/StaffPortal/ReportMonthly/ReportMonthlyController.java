/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.ReportMonthly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class ReportMonthlyController {
    ReportMonthlyPanel rmp;
    ReportMonthlyService rms;
    
    public ReportMonthlyController(ReportMonthlyPanel rmp){
        this.rmp=rmp;
        this.rmp.buttonListener(new ButtonEvent());
        rms = new ReportMonthlyServiceImplementation(rmp);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == rmp.srch){
                rms.displayMonthlyCharts();
            }
            else if(e.getSource() == rmp.prnt){
                rms.printToPDF();
            }
        }
        
    }
}
