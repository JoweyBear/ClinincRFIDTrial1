/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ClinicHub.Appointment;
import StaffPortal.Secretary.SecretaryModel;
import StaffPortal.Student.StudentModel;
import java.sql.ResultSet;
/**
 *
 * @author joena
 */
public interface AppointmentDAO {
    ResultSet fetchAll(String student_id);
//    StudentModel fetchStudentInfo(String student_id);
    SecretaryModel fetchSecretaryInfo(String sec_id);
}
