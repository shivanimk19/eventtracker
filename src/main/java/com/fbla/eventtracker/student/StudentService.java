package com.fbla.eventtracker.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  This class represents the Service class for the student pages
 *  Controls the data flow between the studentController and the studentRepository.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructor for the Student Service with autowired repositories.
     * @param studentRepository
     */
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    /**
     * @return Returns all the students from the studentRepository
     */
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    /**
     * Saves a new student to the studentRepository.
     * Checks if email is already registered
     * Checks if student ID is already registered
     * Makes sure email is a Commack email
     * @param student New student to be added
     * @throws Exception when fails
     */
    public void addNewStudent(Student student) throws Exception {
        Optional<Student> studentOptional = studentRepository.
                findStudentByEmail(student.getEmail());
        // make sure you have a Commack email
        if(studentOptional.isPresent())
            throw new Exception("Email already registered");
        Student studentIdCheck = studentRepository.findStudentByStudentId(student.getStudentId());
        if(studentIdCheck != null)
            throw new Exception("Student ID already registered");
        if(!(student.getEmail().contains("@commack.k12.ny.us")))
            throw new Exception("invalid email");

        studentRepository.save(student);
    }

    /**
     * Returns a student object, given the email and password  (for login)
     * @param email     given email
     * @param passwd    given password
     * @return Returns a student object, given the email and password
     */
    public Student getStudent(String email, String passwd) {
        Optional<Student> studentOptional = studentRepository.
                findStudentByEmail(email);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            // check passwd
            if (student.getPasswd().equals(passwd)) {
                return student;
            } else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * @param email given email
     * @return Returns a student object, given the student email
     */
    public Student getStudent(String email) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student;
        }
        else {
            return null;
        }
    }

    /**
     * @param studentIds given studentIds
     * @return Returns a list of student objects, given a list of studentIds
     */
    public List<Student> getStudentsByIds(List<Integer> studentIds) {
        return studentRepository.findByStudentIdIn(studentIds);
    }

    /**
     * @param studentIds given studentIds
     * @return Returns a list of student objects, given a list of studentIds
     */
    public List<Student> getStudentsNotInIds(List<Integer> studentIds) {
        return studentRepository.findByStudentIdNotIn(studentIds);
    }

    /**
     * Updates the studentRepository points column by adding the new points
     * @param newPoints     new points to add
     * @param student       student to add points to
     */
    public void updatePoints(int newPoints, Student student) {
        studentRepository.updatePoints(newPoints, student.getStudentId());
    }

    /**
     * @param grade Given grade
     * @return Returns a list of student object given the grade
     */
    public List<Student> getStudentsByGrade(int grade) {
        return studentRepository.findByStudentGrade(grade);
    }

    /**
     * @param sid Given studentID
     * @return Returns a student object given the studentId
     */
    public Student findStudentByStudentId(int sid) {
        return studentRepository.findStudentByStudentId(sid);
    }

    /**
     * @param grade Given student grade
     * @return Returns a list of Student objects (top students), given the grade
     */
    public List<Student> findTopStudentByGrade(int grade) {
        return studentRepository.findTopStudentByGrade(grade);
    }

    /**
     * @param grade given student grade
     * @return Returns a list of student objects (leader students), given the grade
     */
    public List<Student> getLeaderStudentsByGrade(int grade) {
        return studentRepository.findLeaderStudentGrade(grade);
    }
}
