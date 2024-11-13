/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ClinicHub.Consultation;

import StaffPortal.Appointment.AppointmentModel;
import StaffPortal.Secretary.SecretaryModel;

/**
 *
 * @author joena
 */
public interface ConsultationDAO {
        SecretaryModel fetchSecretaryInfo(String sec_id);
        void save(AppointmentModel appt);
}
