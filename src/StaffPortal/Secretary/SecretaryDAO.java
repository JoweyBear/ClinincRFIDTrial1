/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.Secretary;
import java.sql.ResultSet;
/**
 *
 * @author richelle
 */
public interface SecretaryDAO {
    ResultSet fetchAll();
    void save(SecretaryModel sec);
    void update(SecretaryModel sec);
    void delete (String secID);
    
}
