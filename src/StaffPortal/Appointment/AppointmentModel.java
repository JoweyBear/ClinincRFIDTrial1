/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffPortal.Appointment;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author joena
 */
public class AppointmentModel {
    private int appt_id;
    private String stud_id;
    private String stud_fname;
    private String stud_mname;
    private String stud_lname;
    private String staff_id;
    private String purpose;
    private String status;
    private Date date;
    private Time time;

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getStud_fname() {
        return stud_fname;
    }

    public void setStud_fname(String stud_fname) {
        this.stud_fname = stud_fname;
    }

    public String getStud_mname() {
        return stud_mname;
    }

    public void setStud_mname(String stud_mname) {
        this.stud_mname = stud_mname;
    }

    public String getStud_lname() {
        return stud_lname;
    }

    public void setStud_lname(String stud_lname) {
        this.stud_lname = stud_lname;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
  
}
