/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Secretary;

import StaffPortal.Secretary.Views.*;
import Util.QuickSearch;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author richelle
 */
public class SecretaryServiceImplementation implements SecretaryService {

    SecretaryDAO dao = new SecretaryDAOImplementation();
    SecretaryPanel sp;
    AddSecretary as;
    UpdateSecretary us;
    
    SecretaryServiceImplementation(SecretaryPanel sp, AddSecretary as, UpdateSecretary us){
        this.sp = sp;
        this.as = as;
        this.us = us;
        
        setTableData();
    }
    
    @Override
    public void setTableData() {
         ResultSet rs = dao.fetchAll();
        sp.sectable.setModel(DbUtils.resultSetToTableModel(rs));
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor tce = sp.sectable.getDefaultEditor(c);
            if (tce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        new QuickSearch(sp, sp.sectable, sp.searchfield, rs);
    }

    @Override
    public void save() {
          if(as.fnametf.getText().equals("")
                ||as.mnametf.getText().equals("")
                ||as.lnametf.getText().equals("")
                ||as.college.getSelectedItem().equals("")
                ||as.numtf.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            SecretaryModel sec = new SecretaryModel();
            sec.setSecID(as.idtf.getText());
            sec.setFName(as.fnametf.getText());
            sec.setMName(as.mnametf.getText());
            sec.setLName(as.lnametf.getText());
            sec.setCollege((String) as.college.getSelectedItem());
            sec.setNumber(as.numtf.getText());
            dao.save(sec);
            setTableData();
        }
    }

    @Override
    public void editView() {
         int dataRow = sp.sectable.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = sp.sectable.getValueAt(dataRow,0) + "";
            us.idtf.setText(id);
            us.fnametf.setText((String) sp.sectable.getValueAt(dataRow,1));
            us.mnametf.setText((String) sp.sectable.getValueAt(dataRow,2));
            us.lnametf.setText((String) sp.sectable.getValueAt(dataRow,3));
            us.college.setSelectedItem((String) sp.sectable.getValueAt(dataRow,4));
            us.numtf.setText((String) sp.sectable.getValueAt(dataRow,5));
            us.setTitle("Edit Information of Owner ID: " + id);
            us.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            us.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select owner to edit.");
        }
    }

    @Override
    public void update() {
       if(us.idtf.getText().equals("")
                ||us.fnametf.getText().equals("")
                ||us.mnametf.getText().equals("")
                ||us.lnametf.getText().equals("")
                ||us.numtf.getText().equals("")
                ||us.college.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            SecretaryModel sec = new SecretaryModel();
            int dataRow = sp.sectable.getSelectedRow();
            sec.setSecID(sp.sectable.getValueAt(dataRow,0).toString());
            sec.setFName(us.fnametf.getText());
            sec.setMName(us.mnametf.getText());
            sec.setLName(us.lnametf.getText());
            sec.setNumber(us.numtf.getText());
            sec.setCollege(us.college.getSelectedItem().toString());
            dao.update(sec);
            setTableData();
        }
    }

    @Override
    public void delete() {
        int dataRow = sp.sectable.getSelectedRow();
        if(dataRow >= 0)
        {
            String secid = sp.sectable.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                               + "Delete Secretary: " + secid + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                dao.delete(secid);
                setTableData();
            }  
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select secretary to delete.");
        }
    }
    
}
