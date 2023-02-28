package com.fbla.eventtracker.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class represents the repository for the EventAttendance table in the database
 */
@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Integer> {

    @Query("SELECT s FROM EventAttendance s WHERE s.studentId = :studentId")
    List<EventAttendance> findEventIdsByStudentId(@Param("studentId") int sid);

    @Query("SELECT s FROM EventAttendance s WHERE s.eventId = :eventId")
    List<EventAttendance> findEventAttendanceByEventId(@Param("eventId") int eid);
}
