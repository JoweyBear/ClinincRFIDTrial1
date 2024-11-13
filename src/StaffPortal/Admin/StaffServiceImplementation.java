/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Admin;

import StaffPortal.Admin.Views.*;
import Util.*;
import java.awt.Dialog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author joena
 */
public class StaffServiceImplementation implements StaffService {
    
    StaffDAO dao = new StaffDAOImplementation();
    PassHash ph = new PassHash();
    StaffPanel sp;
    AddStaff as;
    UpdateStaff us;
    
    StaffServiceImplementation(StaffPanel sp, AddStaff as,  UpdateStaff us){
        this.sp = sp;
        this. as = as;
        this.us = us;
        
        setTableData();
    }
    
        
    @Override
    public void setTableData() {
        ResultSet rs = dao.fetchAll();
        sp.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor ce = sp.jTable1.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        new QuickSearch(sp, sp.jTable1, sp.srch, rs);
    }

    @Override
    public void save() {
        if(as.pass.getText().equals(as.cPass.getText())){
            if(as.staff_id.getText().trim().equals("")
                || as.stFname.getText().trim().equals("")
                || as.stMname.getText().trim().equals("")
                || as.stLname.getText().trim().equals("")
                || as.position.getText().trim().equals("")
                || as.pass.getText().trim().equals("")
                || as.cPass.getText().trim().equals("")
                    ){
                JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                StaffModel staff = new StaffModel();
                staff.setStaff_id(as.staff_id.getText());
                staff.setStFname(as.stFname.getText());
                staff.setStMname(as.stMname.getText());
                staff.setStLname(as.stLname.getText());
                staff.setPosition(as.position.getText());
                staff.setUsername(as.username.getText());
                staff.setPass(ph.passHash(us.cPass.getText()));
                dao.save(staff);
                setTableData();
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Password do not match!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void editView() {
        int dataRow = sp.jTable1.getSelectedRow();
        if(dataRow >= 0)
        {
            String staff_id = sp.jTable1.getValueAt(dataRow,0) + "";
            us.staff_id.setText(staff_id);
            us.stFname.setText((String) sp.jTable1.getValueAt(dataRow, 1));
            us.stMname.setText((String) sp.jTable1.getValueAt(dataRow, 2));
            us.stLname.setText((String) sp.jTable1.getValueAt(dataRow, 3));
            us.position.setText((String) sp.jTable1.getValueAt(dataRow, 4));
            us.username.setText((String) sp.jTable1.getValueAt(dataRow, 5));
            us.setTitle("Edit Information of Admin ID: " + staff_id);
            us.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            us.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select staff to edit.");
        }
    }

    @Override
    public void update() {
        if(us.pass.getText().equals(us.cPass.getText())){
            if((us.staff_id.getText().trim().equals("")
                || us.stFname.getText().trim().equals("")
                || us.stMname.getText().trim().equals("")
                || us.stLname.getText().trim().equals("")
                || us.position.getText().trim().equals("")
                || us.pass.getText().trim().equals("")
                || us.cPass.getText().trim().equals(""))){
                JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
                System.out.println("Staff ID: " + as.staff_id.getText());
                System.out.println("First Name: " + as.stFname.getText());
                System.out.println("Middle Name: " + as.stMname.getText());
                System.out.println("Last Name: " + as.stLname.getText());
                System.out.println("Position: " + as.position.getText());
                System.out.println("Password: " + as.pass.getText());
                System.out.println("Confirm Password: " + as.cPass.getText());
            }
            else{
                StaffModel staff = new StaffModel();
                 int dataRow = sp.jTable1.getSelectedRow();
                staff.setStaff_id((String) sp.jTable1.getValueAt(dataRow, 0));
                staff.setStFname(us.stFname.getText());
                staff.setStMname(us.stMname.getText());
                staff.setStLname(us.stLname.getText());
                staff.setPosition(us.position.getText());
                staff.setUsername(us.username.getText());
                staff.setPass(ph.passHash(us.cPass.getText()));
                dao.update(staff);
                setTableData();
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Password do not match!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete() {
        int dataRow = sp.jTable1.getSelectedRow();
        if(dataRow >= 0)
        {
            String staff_id = sp.jTable1.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                               + "Delete Staff: " + staff_id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                dao.delete(staff_id);
                setTableData();
            }  
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select staff to delete.");
        }
    }   
}
