package com.fbla.eventtracker.events;

import jakarta.persistence.*;


/**
 * This class represents an event attendance record in the database
 * table 'EventAttendance'
 */
@Entity
@Table
public class EventAttendance {
    @Id
    @SequenceGenerator(
            name = "ea_sequence",
            sequenceName = "ea_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ea_sequence"
    )
    private int id;
    private int studentId;
    private int eventId;

    public EventAttendance() {
    }

    public EventAttendance(int studentId, int eventId) {
        this.studentId = studentId;
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
