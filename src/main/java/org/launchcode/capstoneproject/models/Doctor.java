package org.launchcode.capstoneproject.models;

public class Doctor {

    private int id;

    private String doctorname;

    private String password;

    public Doctor(){}

    public int getId() {
        return id;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
