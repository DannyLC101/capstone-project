package org.launchcode.capstoneproject.models;

import javax.persistence.*;
@Entity
@Table(name="user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_role_id")
    private Long userroleid;

    @Column(name="userid")
    private Long userid;

    @Column(name="role")
    private String role;

    @Column(name="username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getUserid() {
        return userid;
    }
    public void setUserid(Long userid) {
        this.userid = userid;
    }
    public Long getUserroleid() {
        return userroleid;
    }
    public void setUserroleid(Long userroleid) {
        this.userroleid = userroleid;
    }
}
