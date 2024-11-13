/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ClinicHub.Main;

import StaffPortal.Student.StudentModel;

/**
 *
 * @author joena
 */
public interface MainframeDAO {
    boolean RFIDMatch(String stud_id);
    StudentModel getStudentInformation(String stud_id);
//    void save(AppointmentModel appt);
}
