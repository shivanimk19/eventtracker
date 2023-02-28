package com.fbla.eventtracker.admin;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for the Winners and prizes page.
 * Controls the data flow between the winnerController and the winnerRepository.
 */
@Service
public class WinnerService {
    private final WinnerRepository winnerRepository;

    /**
     * Constructor with autowired depository
     * @param winnerRepository
     */
    public WinnerService(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    /**
     * Deletes previous winners from the winnerRepository
     * Saves all the new winners to the winnerRepository
     * @param winners list to be saved
     */
    void saveWinners(List<Winner> winners) {
        winnerRepository.deleteAll();;
        winnerRepository.saveAll(winners);
    }

    /**
     * Deletes all the winners from the winnerRepository
     */
    void deleteWinners() {
        winnerRepository.deleteAll();;
    }

    /**
     * Gets all the winners from the winnerRepository
     * @return Returns a list of all the winners
     */
    public List<Winner> getWinners() {
        return winnerRepository.findAll();
    }
}
