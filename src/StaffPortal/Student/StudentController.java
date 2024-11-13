/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Student;

import StaffPortal.Student.Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author joena
 */
public class StudentController {
    StudentService ss;
    StudentPanel sp;
    AddStudent as;
    EditStudent es;
    
    public StudentController(StudentPanel sp, AddStudent as, EditStudent es){
       this.sp = sp;
       this.as = as;
       this.es = es;
       
       this.sp.buttonListener(new ButtonEvent());
       this.as.buttonListener(new ButtonEvent());
       this.es.buttonListener(new ButtonEvent());
       
       ss = new StudentServiceImplementation(sp, as, es);
    }
    class ButtonEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == sp.addstudent){
                as.setVisible(true);
            }
            else if(e.getSource() == sp.editstudent){
                ss.editView();
            }
            else if(e.getSource() == sp.deletestudent){
                ss.delete();
            }
            else if(e.getSource() == as.save){
                ss.save();
            }
            else if(e.getSource() == es.update){
                ss.update();
            }
        }
        
    }
}
