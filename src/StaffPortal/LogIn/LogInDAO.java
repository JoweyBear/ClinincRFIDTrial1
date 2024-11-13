/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.LogIn;

import StaffPortal.Admin.StaffModel;

/**
 *
 * @author joena
 */
public interface LogInDAO {
    StaffModel staffLogin(String user, String pass);
}
