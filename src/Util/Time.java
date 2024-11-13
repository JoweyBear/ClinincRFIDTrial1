/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import StaffPortal.Dashboard;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author joena
 */
public class Time {
    Dashboard dashboard;
    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DateFormat tf = new SimpleDateFormat("hh:mm:ss a");
    
    public Time(Dashboard dashboard)
    {
        this.dashboard = dashboard;
    }

    public void setTime()
    {  
        Worker worker = new Worker();
        worker.start();
    }
    
    class Worker extends Thread 
    {
        @Override
        public void run() {
            while(true)
            {
                Date dateobj = new Date();
                dashboard.dt.setText(df.format(dateobj));
                dashboard.tm.setText(tf.format(dateobj));
                
                // Sleep for a while
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Interrupted exception will occur if
                    // the Worker object's interrupt() method
                    // is called. interrupt() is inherited
                    // from the Thread class.
                    break;
                }
            }
        }
    }
    
}
