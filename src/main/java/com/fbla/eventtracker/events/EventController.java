package com.fbla.eventtracker.events;

import com.fbla.eventtracker.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the controller for the events HTTP pages and takes care of
 * http requests (GET, POST etc)
 */
@RestController
@RequestMapping
public class EventController {
    private final EventService eventService;

    /**
     * Constructor for the Controller with autowired services
     * @param eventService
     */
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Gets all the events from the eventService
     * @return Returns all the events from the eventService
     */
    @GetMapping("/events")
    public List<Event> getEvents(){
        return eventService.getEvents();
    }

}
