package com.fbla.eventtracker.student;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * This class represents the repository for the Student table in the database
 */
@Repository
@Transactional
public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.studentId in :studentIds")
    List<Student> findByStudentIdIn(@Param("studentIds") List<Integer> studentIds);

    List<Student> findByStudentIdNotIn(@Param("studentIds") List<Integer> studentIds);

    @Modifying(clearAutomatically = true)
    @Query("update Student s set s.points = :newPoints where s.studentId = :sid")
    int updatePoints(@Param("newPoints") Integer newPoints, @Param("sid") int sid);

    @Query("SELECT s FROM Student s WHERE s.grade = :grade order by points DESC")
    List<Student> findByStudentGrade(@Param("grade") int grade);

    @Query("SELECT s FROM Student s WHERE s.studentId = ?1")
    Student findStudentByStudentId(int studentId);

    @Query("SELECT s FROM Student s where points = (SELECT MAX(s.points) FROM Student s WHERE s.grade = ?1) and grade = ?1")
    List<Student> findTopStudentByGrade(int grade);

    @Query("SELECT s FROM Student s WHERE s.grade = :grade order by points DESC")
    List<Student> findLeaderStudentGrade(int grade);
}
