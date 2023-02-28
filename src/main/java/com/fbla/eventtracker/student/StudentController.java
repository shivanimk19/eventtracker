package com.fbla.eventtracker.student;


import com.fbla.eventtracker.events.Event;
import com.fbla.eventtracker.events.EventAttendanceService;
import com.fbla.eventtracker.events.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller class for the student user and student pages
 */
@Controller
@RequestMapping
public class StudentController {
	private final StudentService studentService;
	private final EventService eventService;
	private final EventAttendanceService eventAttendanceService;

	/**
	 * Constructor for the Student Controller with autowired services.
	 * Controls the student login and pages.
	 * Controls the data flow between the student html pages and the backend java code.
	 * @param studentService
	 * @param eventService
	 * @param eventAttendanceService
	 */
	@Autowired
	public StudentController(StudentService studentService, EventService eventService, EventAttendanceService eventAttendanceService) {
		this.studentService = studentService;
		this.eventService = eventService;
		this.eventAttendanceService = eventAttendanceService;
	}

	/**
	 * Gets all the students from the student service.
	 * @return Returns a list of all the students from the studentService.
	 */
	@GetMapping("/students")
	public List<Student> getStudents(){
		return studentService.getStudents();
	}

	@GetMapping("/id")
	public String getStudent(String email, Model model){
		Student student = studentService.getStudent(email);
		if (student != null) {
			model.addAttribute("firstname", student.getFname());
			model.addAttribute("lastname", student.getLname());
			model.addAttribute("grade", student.getGrade());
			return "profile";
		} else {
			model.addAttribute("error", "Incorrect email or password");
			return "redirect:/";
		}
	}

	/**
	 * Returns the user to the register page
	 * @return Returns html register page for Thymeleaf.
	 * @throws Exception
	 */
	@PostMapping("/form")
	public String registerForm() throws Exception {
		return "register";
	}

	/**
	 * Returns the user to the student profile page after registration.
	 * @param student student being registered
	 * @param session httpsession
	 * @return Returns html student profile page for Thymeleaf if registration is valid.
	 * Returns the register page again if the registration is invalid.
	 */
	@PostMapping("/register")
	public String registerNewStudent(@ModelAttribute Student student, HttpSession session, Model model) {
		try {
			studentService.addNewStudent(student);
			session.setAttribute("student", student);
			session.setAttribute("login", "student");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
		return "profile";
	}

	/**
	 * Returns the user to the student profile page.
	 * @return Returns html student profile page for Thymeleaf.
	 * @throws Exception
	 */
	@PostMapping("/profile")
	public String getStudent() throws Exception {
		return "profile";
	}

	/**
	 * Takes the user to the student profile page after login.
	 * @param email 			email from student login
	 * @param passwd 			password from student login
	 * @param session			httpsession
	 * @param redirectAttrs 	error message
	 * @return Returns html student profile page for Thymeleaf.
	 * @throws Exception
	 */
	@PostMapping("/login")
	public String login(String email, String passwd, HttpSession session, RedirectAttributes redirectAttrs) throws Exception {
		Student student = studentService.getStudent(email, passwd);
		if (student != null) {
			List<Event> events = eventAttendanceService.getEventsAttendedByStudent(student.getStudentId());
			session.setAttribute("eventsAttended", events);
			session.setAttribute("login", "student");
			session.setAttribute("student", student);
			return "profile";
		} else {
			redirectAttrs.addFlashAttribute("message", "Incorrect email or password");
			return "redirect:/";
		}
	}

	/**
	 * Returns the user to the student events page to view upcoming events.
	 * Passes all the upcoming events from the eventService to the front end through the data model.
	 * @param model data model
	 * @return Returns html student events page for Thymeleaf.
	 */
	@PostMapping("/studentevents")
	public String studentevents(Model model){
		List<Event> allEvents = eventService.getUpcomingEvents();
		model.addAttribute("allEvents", allEvents);
		return "events";
	}

	/**
	 * Logs out the user.
	 * Invalidates the session.
	 * Returns the user back to the home page.
	 * @param session httpsession
	 * @return Returns html login page for Thymeleaf.
	 */
	@PostMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * @return Returns the user to the login/index page when the application starts.
	 */
	@GetMapping
	public String index() {
		return "index";
	}
}
