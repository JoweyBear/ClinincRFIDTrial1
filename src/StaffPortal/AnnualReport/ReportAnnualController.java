/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.AnnualReport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class ReportAnnualController {
    ReportAnnualPanel rap;
    ReportAnnualService ras;
    
    public ReportAnnualController(ReportAnnualPanel rap){
        this.rap = rap;
        this.rap.buttonListener(new ButtonEvent());
        ras = new ReportAnnualServiceImplementation(rap);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == rap.srch){
                ras.displayYearlyCharts();
            }
            else if(e.getSource() == rap.prnt){
                ras.printToPDF();
            }
        }
        
    }
}
