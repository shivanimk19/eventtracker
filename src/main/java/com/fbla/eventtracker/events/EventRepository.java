package com.fbla.eventtracker.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the repository for the Event table in the database
 */
@Repository
public interface EventRepository
    extends JpaRepository<Event, Integer> {

    @Query("SELECT s FROM Event s WHERE s.id = ?1")
    Optional<Event> findEventByID(int id);

    @Query("SELECT e from Event e where e.eventDateTime > :currentDateTime order by e.eventDateTime")
    List<Event> findUpcomingEvents(@Param("currentDateTime") LocalDateTime currentDateTime);

    @Query("SELECT e from Event e ORDER by e.eventDateTime DESC")
    List<Event> getOrderedEvents();

    @Query("SELECT e from Event e where e.id in  ?1")
    List<Event> getEventsForIds(List<Integer> eventIds);
}
