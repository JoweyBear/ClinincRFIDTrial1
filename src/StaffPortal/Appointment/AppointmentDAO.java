/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package StaffPortal.Appointment;

import java.sql.ResultSet;
/**
 *
 * @author joena & jheza
 */
public interface AppointmentDAO {
    ResultSet fetchAll();
    ResultSet searchByDate(String date1, String date2);
    void save(AppointmentModel appmt);
    void update(AppointmentModel appmt);
    void delete(int appointment_id);
}
