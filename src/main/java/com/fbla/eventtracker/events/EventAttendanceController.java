package com.fbla.eventtracker.events;

import com.fbla.eventtracker.student.Student;
import com.fbla.eventtracker.student.StudentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the controller for the event check in HTTP pages and takes care of
 * http requests (GET, POST etc)
 */
@Controller
@RequestMapping
public class EventAttendanceController {
    private final EventAttendanceService eventAttendanceService;
    private final EventService eventService;
    private final StudentService studentService;

    /**
     * Constructor for the Controller with autowired services
     * @param eventAttendanceService
     * @param eventService
     * @param studentService
     */
    @Autowired
    public EventAttendanceController(EventAttendanceService eventAttendanceService, EventService eventService, StudentService studentService) {
        this.eventAttendanceService = eventAttendanceService;
        this.eventService = eventService;
        this.studentService = studentService;
    }

    /**
     * @param id studentID
     * @return Returns a list of the events attended by a certain student given the student ID
     */
    public List<Event> getEventsAttendedByStudent(int id) {
        List<EventAttendance> eventAttendances= eventAttendanceService.getEventsAttendanceByStudent(id);
        List<Integer> eventIds = new ArrayList<>();
        for (EventAttendance ea: eventAttendances) {
            int eventId = ea.getEventId();
            eventIds.add(eventId);
        }
        return eventService.getEventsForIds(eventIds);
    }

    /**
     * Gets the number of points for the event.
     * Updates the points of the students that attended the event.
     * Returns the user to the admin Events page.
     * @param eventId       id of the event for checkin
     * @param studentIds    list of the studentIDs that attended the event
     * @param model         data model
     * @return Returns html admin events page for Thymeleaf.
     */
    @PostMapping("/savecheckin")
    public String savecheckin(@RequestParam Integer eventId,
                              @NotNull @RequestParam(value = "idChecked") List<Integer> studentIds, Model model) {

        eventAttendanceService.addEventAttendance(eventId, studentIds);
        Optional<Event> eventOptional = eventService.getEventsForId(eventId);
        int eventPoints = 0;
        if (eventOptional.isPresent()) {
            eventPoints = eventOptional.get().getPoints();
        }
        List<Student> studentList = studentService.getStudentsByIds(studentIds);
        for( Student student : studentList){
            int newPoints = student.getPoints() + eventPoints;
            studentService.updatePoints(newPoints, student);
        }
        List<Event> allEvents = eventService.getEvents();
        model.addAttribute("allEvents", allEvents);
        return "adminevents";
    }

}
