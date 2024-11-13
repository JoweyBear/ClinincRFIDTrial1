/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import StaffPortal.Admin.StaffModel;
import StaffPortal.Student.StudentModel;
import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author joena
 */
public class GlobalVar {
    public static SerialPort port;
    public static StaffModel loggedInStaff;
    public static StudentModel activeStudent;
}
