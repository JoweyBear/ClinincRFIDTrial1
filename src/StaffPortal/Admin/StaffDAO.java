/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.Admin;
import java.sql.ResultSet;
/**
 *
 * @author joena
 */
public interface StaffDAO {
    ResultSet fetchAll();
    void save(StaffModel staff);
    void update(StaffModel staff);
    void delete(String staff_id);
    
}
