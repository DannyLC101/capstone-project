package org.launchcode.capstoneproject.models;


//import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Entity
public class UserSignUp {

//    @Id
//    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=7, max=20)
    private String usertname;

    @NotNull

    private String email;

    @NotNull(message = "Please enter the password")
    @Size(min=7, max=20)
    private String password;

    public UserSignUp(){}

    public int getId() {
        return id;
    }

    public String getUsertname() {
        return usertname;
    }

    public void setUsertname(String usertname) {
        this.usertname = usertname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
