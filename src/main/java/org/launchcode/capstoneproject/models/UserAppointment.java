package org.launchcode.capstoneproject.models;

import javax.persistence.*;

@Entity
@Table(name="user_appointment")
public class UserAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="appointmentid")
    private Long appointmentid;

    @Column(name="appointtime")
    private String appointtime;

    @Column(name="status")
    private boolean status;

    @Column(name="username")
    private String username;

    @Transient
    public String selected;

    public Long getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(Long appointmentid) {
        this.appointmentid = appointmentid;
    }

    public String getAppointtime() {
        return appointtime;
    }

    public void setAppointtime(String appointtime) {
        this.appointtime = appointtime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "UserAppointment{" +
                "appointmentid=" + appointmentid +
                ", appointtime='" + appointtime + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }
}
