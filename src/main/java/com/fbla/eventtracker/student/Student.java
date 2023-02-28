package com.fbla.eventtracker.student;

import jakarta.persistence.*;

/**
 * This class represents a student record in the database
 * table 'Student'
 */
@Entity
@Table
public class Student {
    @Id
    private int studentId;
    private String fname;
    private String lname;

    private String passwd;
    private int grade;
    private String email;

    private int points;

    public Student() {
    }

    public Student(String fname, String lname, int studentId, String passwd, int grade, String email) {
        this.fname = fname;
        this.lname = lname;
        this.studentId = studentId;
        this.grade = grade;
        this.email = email;
        this.passwd = passwd;
    }

    public String getFname() {
        return fname;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public String getEmail() {
        return email;
    }

    public String getLname() {
        return lname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
