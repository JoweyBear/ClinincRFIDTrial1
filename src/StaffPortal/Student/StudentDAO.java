/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.Student;
import java.sql.ResultSet;
/**
 *
 * @author estip
 */
public interface StudentDAO {
    ResultSet fetchAll();
    void save(StudentModel student);
    void update(StudentModel student);
    void delete(String student_id);
}