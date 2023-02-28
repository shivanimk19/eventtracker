package com.fbla.eventtracker.admin;

import com.fbla.eventtracker.events.Event;
import com.fbla.eventtracker.events.EventAttendance;
import com.fbla.eventtracker.events.EventAttendanceService;
import com.fbla.eventtracker.events.EventService;
import com.fbla.eventtracker.student.Student;
import com.fbla.eventtracker.student.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the controller for the admin user and admin HTML pages and takes care of
 * http requests (GET, POST etc)
 */
@Controller
@RequestMapping
public class AdminController {
    private final AdminService adminService;
    private final EventService eventService;
    private final StudentService studentService;
    private final EventAttendanceService eventAttendanceService;
    private final WinnerService winnerService;

    /**
     * Constructor for the Admin Controller with autowired services.
     * Controls the admin login and pages.
     * Controls the data flow between the admin html pages and the backend java code.
     * @param adminService
     * @param eventService
     * @param studentService
     * @param eventAttendanceService
     * @param winnerService
     */
    @Autowired
    public AdminController(AdminService adminService, EventService eventService, StudentService studentService, EventAttendanceService eventAttendanceService, WinnerService winnerService) {
        this.adminService = adminService;
        this.eventService = eventService;
        this.studentService = studentService;
        this.eventAttendanceService = eventAttendanceService;
        this.winnerService = winnerService;
    }

    /**
     * This method logs admin user and takes to the admin profile page
     * if it is is a valid admin login.
     * @param email          admin login email
     * @param passwd         admin login passwd
     * @param session        http session
     * @param redirectAttrs  redirect attributes
     * @return Returns html admin profile page for Thymeleaf.
     * @throws Exception
     */
    @PostMapping("/adminlogin")
    public String login(String email, String passwd, HttpSession session, RedirectAttributes redirectAttrs) throws Exception {
        Admin admin = adminService.getAdmin(email, passwd);
        if (admin != null) {
            session.setAttribute("login", "admin");
            session.setAttribute("firstname", admin.getFname());
            session.setAttribute("lastname", admin.getLname());
            return "adminprofile";
        } else {
            redirectAttrs.addFlashAttribute("message", "Incorrect email or password");
            return "redirect:/";
        }
    }

    /**
     * Returns user to the admin profile page.
     * @return Returns html admin profile page for Thymeleaf.
     */
    @PostMapping("/adminprofile")
    public String adminprofile(){
        return "adminprofile";
    }

    /**
     * Returns user to the admin events page.
     * Passes all the events from the eventService to the front end through the data model.
     * @param model data model
     * @return Returns html admin events page for Thymeleaf.
     */
    @PostMapping("/adminevents")
    public String adminevents(Model model){
        List<Event> allEvents = eventService.getEvents();
        model.addAttribute("allEvents", allEvents);
        return "adminevents";
    }

    /**
     * Returns user to the admin events page after adding a new event.
     * Passes all the events from the eventService to the front end through the data model.
     * @param event event to be added
     * @param model data model
     * @return Returns html admin events page for Thymeleaf.
     */
    @PostMapping("/addevent")
    public String addEvent(@ModelAttribute Event event, Model model){
        eventService.addEvent(event);
        List<Event> allEvents = eventService.getEvents();
        model.addAttribute("allEvents", allEvents);
        return "adminevents";
    }

    /**
     * Returns user to the admin check in page.
     * Passes the name of the event from the eventService to the front end through the data model.
     * Passes the checked in and un-checked in students to the front end through the data model.
     * @param eventId eventId for checkin
     * @param model   data model
     * @return Returns html check in page for Thymeleaf.
     */
    @PostMapping("/checkin")
    public String checkin(@RequestParam Integer eventId, Model model){
        Optional<Event> eventOptional = eventService.getEventsForId(eventId);
        if (eventOptional.isPresent()) {
            model.addAttribute("event", eventOptional.get());
        }
        List<EventAttendance> eventAttendanceList = eventAttendanceService.getEventAttendance(eventId);
        List<Integer> attendedStudentIds = new ArrayList<>();
        for (EventAttendance eventAttendance: eventAttendanceList) {
            attendedStudentIds.add(eventAttendance.getStudentId());
        }
        List<Student> checkedStudents = studentService.getStudentsByIds(attendedStudentIds);
        model.addAttribute("checkedInStudents", checkedStudents);
        if (attendedStudentIds.isEmpty()) {
            List<Student> allStudents = studentService.getStudents();
            model.addAttribute("uncheckedStudents", allStudents);
        } else {
            List<Student> uncheckedStudents = studentService.getStudentsNotInIds(attendedStudentIds);
            model.addAttribute("uncheckedStudents", uncheckedStudents);
        }
        return "checkin";
    }

    /**
     * Returns user to the admin log in page.
     * @return Returns html check in page for Thymeleaf.
     * @throws Exception
     */
    @PostMapping("/admin")
    public String admin() throws Exception {
        return "admin";
    }

    /**
     * Picks the top winners and random winners from each grade.
     * Saves winners to the winnerService.
     * Returns user to the admin's prizes page.
     * @param model data model
     * @return Returns html check in page for Thymeleaf.
     * @throws Exception
     */
    @PostMapping("/pickwinners")
    public String pickWinners(Model model) throws Exception {
        List<Winner> winners = new ArrayList<>();
        List<Student> nineTopList = studentService.findTopStudentByGrade(9);
        int randomNum = getRandomNumber(0,nineTopList.size()-1);
        Student nineTop = nineTopList.get(randomNum);
        List<Student> gradeNineStudents = studentService.getStudentsByGrade(9);
        int count = gradeNineStudents.size();
        if (count > 1){
            gradeNineStudents.remove(nineTop);
        }
        randomNum = getRandomNumber(0,count-1);
        Student nineRandom = gradeNineStudents.get(randomNum);
        model.addAttribute("nineRandom", nineRandom);
        model.addAttribute("nineTop", nineTop);
        winners.add(new Winner(9, "random", nineRandom.getStudentId()));
        winners.add(new Winner(9, "top", nineTop.getStudentId()));


        List<Student> tenTopList = studentService.findTopStudentByGrade(10);
        randomNum = getRandomNumber(0,tenTopList.size()-1);
        Student tenTop = tenTopList.get(randomNum);
        List<Student> gradeTenStudents = studentService.getStudentsByGrade(10);
        count = gradeTenStudents.size();
        if (count > 1){
            gradeTenStudents.remove(tenTop);
        }
        randomNum = getRandomNumber(0,count-1);
        Student tenRandom = gradeTenStudents.get(randomNum);
        model.addAttribute("tenRandom", tenRandom);
        model.addAttribute("tenTop", tenTop);
        winners.add(new Winner(10, "random", tenRandom.getStudentId()));
        winners.add(new Winner(10, "top", tenTop.getStudentId()));


        List<Student> elevenTopList = studentService.findTopStudentByGrade(11);
        randomNum = getRandomNumber(0,elevenTopList.size()-1);
        Student elevenTop = elevenTopList.get(randomNum);
        List<Student> gradeElevenStudents = studentService.getStudentsByGrade(11);
        count = gradeElevenStudents.size();
        if (count > 1){
            gradeElevenStudents.remove(elevenTop);
        }
        randomNum = getRandomNumber(0,count-1);
        Student elevenRandom = gradeElevenStudents.get(randomNum);
        model.addAttribute("elevenRandom", elevenRandom);
        model.addAttribute("elevenTop", elevenTop);
        winners.add(new Winner(11, "random", elevenRandom.getStudentId()));
        winners.add(new Winner(11, "top", elevenTop.getStudentId()));

        ;
        List<Student> twelveTopList = studentService.findTopStudentByGrade(12);
        randomNum = getRandomNumber(0,twelveTopList.size()-1);
        Student twelveTop = twelveTopList.get(randomNum);
        List<Student> gradeTwelveStudents = studentService.getStudentsByGrade(12);
        count = gradeTwelveStudents.size();
        if (count > 1){
            gradeTwelveStudents.remove(twelveTop);
        }
        randomNum = getRandomNumber(0,count-1);
        Student twelveRandom = gradeTwelveStudents.get(randomNum);
        model.addAttribute("twelveRandom", twelveRandom);
        model.addAttribute("twelveTop", twelveTop);
        winners.add(new Winner(12, "random", twelveRandom.getStudentId()));
        winners.add(new Winner(12, "top", twelveTop.getStudentId()));

        winnerService.saveWinners(winners);
        return "adminprizes";
    }

    /**
     * Returns the user to the leaderboard page.
     * Passes all the students in the requested grade from the
     * studentService to the front end through the data model.
     * @param grade student grade for leaderboard
     * @param model data model
     * @return Returns html admin leaderboard page for Thymeleaf.
     */
    @PostMapping("/leaderboard")
    public String getLeaderBoard(@RequestParam int grade, HttpSession session, Model model) {
        List<Student> studentsByGrade = studentService.getLeaderStudentsByGrade(grade);
        model.addAttribute("studentsByGrade", studentsByGrade);
        model.addAttribute("leaderboardgrade", grade);
        if (session.getAttribute("login").toString().equalsIgnoreCase("admin")) {
            return "leaderboardadmin";
        }
        else {
            return "leaderboard";
        }
    }

    /**
     * Function created to pick random number from given minimum value to given maximum value
     * @param min minimum value for random number generation
     * @param max maximum value for random number generation
     * @return Returns random int value
     */
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

