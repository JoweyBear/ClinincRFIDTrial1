/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package StaffPortal;

import StaffPortal.Admin.*;
import StaffPortal.Admin.Views.*;
import StaffPortal.AnnualReport.ReportAnnualController;
import StaffPortal.AnnualReport.ReportAnnualPanel;
import StaffPortal.Appointment.*;
import StaffPortal.Appointment.Views.*;
import StaffPortal.ReportMonthly.ReportMonthlyController;
import StaffPortal.ReportMonthly.ReportMonthlyPanel;
import StaffPortal.Reports.*;
import StaffPortal.Secretary.*;
import StaffPortal.Secretary.Views.*;
import StaffPortal.Student.*;
import StaffPortal.Student.Views.*;
import java.awt.CardLayout;
import java.awt.Color;
import javaswingdev.GradientDropdownMenu;
import javaswingdev.MenuEvent;
import javax.swing.JFrame;

/**
 *
 * @author richelle
 */
public class Dashboard extends javax.swing.JFrame {

    ReportSummaryPanel rsp;
    
    public Dashboard(ReportSummaryPanel rsp) {
        this.rsp = rsp;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        CardLayout cl = (CardLayout) (jPanel1.getLayout());
        jPanel1.add(rsp,"ReportSummary Panel");
        cl.show(jPanel1, "ReportSummary Panel");
         
        GradientDropdownMenu menu = new GradientDropdownMenu();
        menu.setBackground(new Color(0, 119, 182));
        menu.setGradientColor(new Color(0, 119, 182), new Color(3, 4, 94));
        menu.setHeaderGradient(false);
        menu.addItem("Home");
        menu.addItem("Appointment");
        menu.addItem("Manage", "Student", "Staff", "Secretary");
        menu.addItem("Report", "Monthly Report", "Annual Report");
        menu.addItem("Log out");
//        menu.addItem("Logs");
        menu.applay(this);
        menu.addEvent(new MenuEvent() {
            @Override
    public void selected(int parentIndex, int childIndex, boolean isSubMenu) {
        if (parentIndex == 0) {
            ReportSummaryPanel rsp = new ReportSummaryPanel();
            new ReportSummaryController(rsp);
            CardLayout cl = (CardLayout) (jPanel1.getLayout());
            jPanel1.add(rsp,"ReportSummary Panel");
            cl.show(jPanel1, "ReportSummary Panel");
        } else if (parentIndex == 1) {
            AppointmentPanel ap = new AppointmentPanel();
            AddAppointment aa = new AddAppointment();
            EditAppointment ea = new EditAppointment();
            new AppointmentController(ap, aa, ea);
            CardLayout cl = (CardLayout) (jPanel1.getLayout());
            jPanel1.add(ap,"Appointment Panel");
            cl.show(jPanel1, "Appointment Panel");
        } else if (parentIndex == 2) {
            if (isSubMenu) {
                if (childIndex == 1) {
                    StudentPanel sp = new StudentPanel();
                    AddStudent as = new AddStudent();
                    EditStudent es = new EditStudent();
                    new StudentController(sp, as, es);
                    CardLayout cl = (CardLayout) (jPanel1.getLayout());
                    jPanel1.add(sp,"Student Panel");
                    cl.show(jPanel1, "Student Panel");
                } else if (childIndex == 2) {
                    StaffPanel sp = new StaffPanel();
                    AddStaff as = new AddStaff();
                    UpdateStaff us = new UpdateStaff();
                    new StaffController(sp, as, us);
                    CardLayout cl = (CardLayout) (jPanel1.getLayout());
                    jPanel1.add(sp,"Staff Panel");
                    cl.show(jPanel1, "Staff Panel"); 
                } else if (childIndex == 3) {
                    SecretaryPanel sp = new SecretaryPanel();
                    AddSecretary as = new AddSecretary();
                    UpdateSecretary us = new UpdateSecretary();
                    new SecretaryController(sp, as, us);
                    CardLayout cl = (CardLayout) (jPanel1.getLayout());
                    jPanel1.add(sp,"Secretary Panel");
                    cl.show(jPanel1, "Secretary Panel"); 
                }
            } else {
//                cardLayout.show(contentPanel, "Manage");
            }
        } else if (parentIndex == 3) {
            if (isSubMenu) {
                if (childIndex == 1) {
                    ReportMonthlyPanel rmp = new ReportMonthlyPanel();
                    new ReportMonthlyController(rmp);
                    CardLayout cl = (CardLayout) (jPanel1.getLayout());
                    jPanel1.add(rmp,"Monthly Report Panel");
                    cl.show(jPanel1, "Monthly Report Panel");
                } else if (childIndex == 2) {
                    ReportAnnualPanel rap = new ReportAnnualPanel();
                    new ReportAnnualController(rap);
                    CardLayout cl = (CardLayout) (jPanel1.getLayout());
                    jPanel1.add(rap,"Report Annual Panel");
                    cl.show(jPanel1, "Report Annual Panel");                                         
                }
            } else {
//                cardLayout.show(contentPanel, "Report");
            }
        } else if (parentIndex == 4) {
                     System.exit(0);
        } 
//                else if (parentIndex == 5) {
//            cardLayout.show(contentPanel, "Logs");
//        } 
                else {
            throw new IllegalArgumentException("Unexpected menu item index: " + parentIndex);
        }
    }

        });
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        stffNm = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dt = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tm = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(144, 224, 239));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Date: ");

        stffNm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        stffNm.setText(" ");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Welcome! ");

        dt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dt.setText(" ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Time:");

        tm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tm.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stffNm, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tm, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(stffNm)
                    .addComponent(dt)
                    .addComponent(tm))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1143, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Dashboard().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel dt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel stffNm;
    public javax.swing.JLabel tm;
    // End of variables declaration//GEN-END:variables
}
