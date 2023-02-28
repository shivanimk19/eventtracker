package com.fbla.eventtracker.admin;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;

/**
 * This class represents a admin user in the database table 'Admin'
 */
@Entity
@Table
@Scope("session")
public class Admin {
    @Id
    @SequenceGenerator(
            name = "admin_sequence",
            sequenceName = "admin_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin_sequence"
    )
    private int id;
    private String fname;
    private String lname;
    private String passwd;
    private String email;

    public Admin(){

    }
    public Admin(String fname, String lname, String passwd, String email) {
        this.fname = fname;
        this.lname = lname;
        this.passwd = passwd;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
