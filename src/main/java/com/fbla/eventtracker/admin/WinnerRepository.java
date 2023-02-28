package com.fbla.eventtracker.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class represents the repository for the winner table in the database
 */
@Repository
public interface WinnerRepository extends JpaRepository<Winner, Integer> {

}
