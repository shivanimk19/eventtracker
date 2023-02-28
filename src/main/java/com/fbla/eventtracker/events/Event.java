package com.fbla.eventtracker.events;
import java.sql.Timestamp;
import java.time.LocalDate; // import the LocalDate class
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;

/**
 * This class represents an event in the database table 'Event'
 */
@Entity
@Table
public class Event {
    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_sequence"
    )

    private int id;
    private String eventName;
    private String eventDescription;
    private String location;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime eventDateTime;
    private int points;

    public Event() {
    }

    public Event(int id) {
        this.id = id;
    }

    public Event(String eventName, String eventDescription, String location, LocalDateTime eventDate, int points) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDateTime = eventDate;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public int getPoints() {
        return points;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
