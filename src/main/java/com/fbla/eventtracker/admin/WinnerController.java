package com.fbla.eventtracker.admin;

import com.fbla.eventtracker.student.Student;
import com.fbla.eventtracker.student.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the controller for the winner HTTP pages and takes care of
 * http requests (GET, POST etc)
 */
@Controller
@RequestMapping
public class WinnerController {
    private final WinnerService winnerService;
    private final StudentRepository studentService;

    @Autowired
    public WinnerController(WinnerService winnerService, StudentRepository studentService) {
        this.winnerService = winnerService;
        this.studentService = studentService;
    }

    /**
     * Returns winners to frontend page via data model properties.
     * @param model    data model object
     * @param session  http session object
     * @return returns to student or admin prizes html for Thymeleaf depending on who is logged in
     */
    @PostMapping("/prizes")
    public String prizes(Model model, HttpSession session) {
        List<Winner> winners = winnerService.getWinners();
        if (winners.isEmpty()) {
            model.addAttribute("winnerChosen", false);
        } else {
            for (int i = 0; i < winners.size(); i++) {
                Winner winner = winners.get(i);
                Student student = studentService.findStudentByStudentId(winner.getStudentId());
                if (winner.getGrade() == 9) {
                    if (winner.getWinType().equalsIgnoreCase("top")) {
                        model.addAttribute("nineTop", student);
                    } else {
                        model.addAttribute("nineRandom", student);
                    }
                }
                if (winner.getGrade() == 10) {
                    if (winner.getWinType().equalsIgnoreCase("top")) {
                        model.addAttribute("tenTop", student);
                    } else {
                        model.addAttribute("tenRandom", student);
                    }
                }
                if (winner.getGrade() == 11) {
                    if (winner.getWinType().equalsIgnoreCase("top")) {
                        model.addAttribute("elevenTop", student);
                    } else {
                        model.addAttribute("elevenRandom", student);
                    }
                }
                if (winner.getGrade() == 12) {
                    if (winner.getWinType().equalsIgnoreCase("top")) {
                        model.addAttribute("twelveTop", student);
                    } else {
                        model.addAttribute("twelveRandom", student);
                    }
                }
            }
        }
        if (session.getAttribute("login").toString().equalsIgnoreCase("admin")) {
            return "adminprizes";
        }
        else {
            return "prizes";
        }
    }

    /**
     * Resets the winners by deleting the records from the winnerService
     * @return Returns html admin prizes page for Thymeleaf.
     */
    @PostMapping("/resetprizes")
    public String prizes() {
        winnerService.deleteWinners();
        return "adminprizes";
    }
}
