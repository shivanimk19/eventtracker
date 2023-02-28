package com.fbla.eventtracker.admin;

import jakarta.persistence.*;

/**
 * This class represents a winner student information row in the database table 'winner'
 */
@Entity
@Table
public class Winner {
    @Id
    @SequenceGenerator(
            name = "winder_sequence",
            sequenceName = "winner_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "winner_sequence"
    )
    private int id;
    private int grade;
    private String winType;
    private int studentId;

    public Winner() {
    }

    public Winner(int grade, String winType, int studentId) {
        this.grade = grade;
        this.winType = winType;
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public String getWinType() {
        return winType;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setWinType(String winType) {
        this.winType = winType;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
