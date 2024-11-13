/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Student;

import StaffPortal.Student.Views.*;
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
 * @author joena
 */
public class StudentServiceImplementation implements StudentService {
    
    StudentDAO dao = new StudentDAOImplementation();
    StudentPanel sp;
    AddStudent as; 
    EditStudent es;
    
    StudentServiceImplementation(StudentPanel sp, AddStudent as, EditStudent es){
    
        this.sp = sp;
        this.as = as;
        this.es = es;
        
        setTableData();
        
    }
    
    @Override
    public void setTableData() {
        ResultSet rs = dao.fetchAll();
       sp.table.setModel(DbUtils.resultSetToTableModel(rs));
             
        for(Class c:Arrays.asList(Object.class, Number.class, Boolean.class))
       {
            TableCellEditor tce = sp.table.getDefaultEditor(c);
            if (tce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) tce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        new QuickSearch(sp, sp.table, sp.srch, rs); 
            
    }
    @Override
    public void save() {
      if(as.fname.getText().equals("")
        ||as.lname.getText().equals("")
        ||as.mname.getText().equals("")
        ||as.college.getSelectedItem().equals("")
        ||as.program.getText().equals("")
        ||as.section.getText().equals("")
        ||as.sex.getSelectedItem().equals("")
        ||as.barangay.getText().equals("")
        ||as.municipal.getText().equals("")
        ||as.guardianName.getText().equals("")
        ||as.guardianNo.getText().equals("")
        ||as.sec_id.getSelectedItem().equals("")){
          JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
      }
      else {
          StudentModel student = new StudentModel();
          student.setStud_id(as.stud_id.getText());
          student.setFname(as.fname.getText());
          student.setLname(as.lname.getText());
          student.setMname(as.mname.getText());
          student.setGender(as.sex.getSelectedItem().toString());
          student.setCollege(as.college.getSelectedItem().toString());
          student.setProgram(as.program.getText());
          student.setBarangay(as.barangay.getText());
          student.setMunicipal(as.municipal.getText());
          student.setSection(as.section.getText());
          student.setGuardian(as.guardianName.getText());
          student.setGuar_no(as.guardianNo.getText());
          String id = as.sec_id.getSelectedItem().toString();
          int origin = id.indexOf(":")+1;
          String sec_id =(id.substring(origin,(id.length()-1)));
          student.setSec_id(sec_id);
         dao.save(student);
         JOptionPane.showMessageDialog(null, "Student Information Saved.");

         setTableData();
      }
    }

    @Override
    public void editView() {
      int dataRow = sp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String stud_id = (sp.table.getValueAt(dataRow,0) + "");
            es.stud_id.setText(stud_id);
            es.fname.setText((String) sp.table.getValueAt(dataRow,1));
            es.mname.setText((String) sp.table.getValueAt(dataRow,2));
            es.lname.setText((String) sp.table.getValueAt(dataRow,3));
            es.sex.setSelectedItem(sp.table.getValueAt(dataRow,4));
            es.section.setText((String) sp.table.getValueAt(dataRow,5));
            es.college.setSelectedItem((String) sp.table.getValueAt(dataRow,6));
            es.program.setText((String) sp.table.getValueAt(dataRow,7));
            es.barangay.setText((String) sp.table.getValueAt(dataRow,8));
            es.municipal.setText((String) sp.table.getValueAt(dataRow,9));
            es.guardianName.setText((String) sp.table.getValueAt(dataRow, 10));
            es.guardianNo.setText((String) sp.table.getValueAt(dataRow, 11));
            es.sec_id.setSelectedItem(sp.table.getValueAt(dataRow, 12));
            es.setTitle("Edit Information of Student ID: " + stud_id);
            es.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            es.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select owner to edit.");
        }
    }

    @Override
    public void update() {
      if(es.fname.getText().equals("")
        ||es.lname.getText().equals("")
        ||es.mname.getText().equals("")
        ||es.college.getSelectedItem().equals("")
        ||es.program.getText().equals("")
        ||es.section.getText().equals("")
        ||es.sex.getSelectedItem().equals("")
        ||es.barangay.getText().equals("")
        ||es.municipal.getText().equals("")
        ||es.guardianName.getText().equals("")
        ||es.guardianNo.getText().equals("")
        ||es.sec_id.getSelectedItem().equals("")){
          JOptionPane.showMessageDialog(null,"Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
      }
      else{
        StudentModel student= new StudentModel();
        int dataRow = sp.table.getSelectedRow();
        student.setStud_id(sp.table.getValueAt(dataRow,0).toString());
        student.setFname(es.fname.getText());
        student.setMname(es.mname.getText());
        student.setLname(es.lname.getText());
        student.setGender(es.sex.getSelectedItem().toString());
        student.setSection(es.section.getText());
        student.setCollege(es.college.getSelectedItem().toString());
        student.setProgram(es.program.getText());
        student.setBarangay(es.barangay.getText());
        student.setMunicipal(es.municipal.getText());
        student.setGuardian(es.guardianName.getText());
        student.setGuar_no(es.guardianNo.getText());
        String id = es.sec_id.getSelectedItem().toString();
        int origin = id.indexOf(":")+1;
        String sec_id =(id.substring(origin,(id.length()-1)));
        student.setSec_id(sec_id);
        dao.update(student);
        JOptionPane.showMessageDialog(null, "Student Information Updated");
        setTableData();
        }
    }

    @Override
    public void delete() {
      int dataRow = sp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String student_id = sp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                               + "Delete Student: " + student_id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                dao.delete(student_id);
                setTableData();
            }  
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select student to delete.");
        }
    }
    
}
