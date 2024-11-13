/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.LogIn;

import StaffPortal.Admin.StaffModel;
import StaffPortal.Appointment.*;
//import Appointment.Views.*;
import StaffPortal.Dashboard;
import StaffPortal.Reports.ReportSummaryController;
import StaffPortal.Reports.ReportSummaryPanel;
import Util.*;
import javax.swing.JFrame;

/**
 *
 * @author joena
 */
public class LogInServiceImplementation implements LogInService {
    LogInDAO dao = new LogInDAOImplementation();
    PassHash ph = new PassHash();
    LogInFrame lf;
    
    LogInServiceImplementation(LogInFrame lf){
       this.lf = lf;
    }
    
    @Override
    public void logIn() {
        String user = lf.username.getText();
        String pass = ph.passHash(lf.pass.getText());
        StaffModel staff = dao.staffLogin(user,pass );
        GlobalVar.loggedInStaff = staff;
            ReportSummaryPanel rsp = new ReportSummaryPanel();
            new ReportSummaryController(rsp);
            Dashboard dashboard = new Dashboard(rsp);
            dashboard.stffNm.setText(GlobalVar.loggedInStaff.getStFname() 
                    + " " + GlobalVar.loggedInStaff.getStLname());
            Time t = new Time(dashboard);
            t.setTime();
            dashboard.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            dashboard.setVisible(true);
            lf.setVisible(false);

    }
    
}
