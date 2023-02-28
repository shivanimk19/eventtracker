package com.fbla.eventtracker.admin;

import com.fbla.eventtracker.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class represents the repository for the admin table in the database
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT s FROM Admin s WHERE s.email = ?1")
    Optional<Admin> findAdminByEmail(String email);

}
