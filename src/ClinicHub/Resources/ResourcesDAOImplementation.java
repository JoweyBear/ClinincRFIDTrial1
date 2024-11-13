/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClinicHub.Resources;

import StaffPortal.Appointment.AppointmentModel;
import Connection.Ticket;
import ClinicHub.Emergency.EmergencyDAOImplementation;
import Util.GlobalVar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joena
 */
public class ResourcesDAOImplementation implements ResourcesDAO {

    @Override
        public void save(AppointmentModel appt) {
                Connection conn = null;
                try {    
                    conn = Ticket.getConn();
                    String sql = "Insert into appointment values (0,?,?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, GlobalVar.activeStudent.getStud_id());
                    stmt.setString(2, appt.getPurpose());
                    stmt.setString(3, appt.getStatus());
                    stmt.setString(4, appt.getStaff_id());
                    stmt.setDate(5, (Date) appt.getDate());
                    stmt.setTime(6, appt.getTime());
                } catch (SQLException ex) {
                    Logger.getLogger(ResourcesDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }    
}
