package com.fbla.eventtracker.admin;

import com.fbla.eventtracker.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *  This class represents the Service class for the admin user and admin pages
 *  Controls the data flow between the adminController and the adminRepository.
 */
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    /**
     * Constructor for the Admin Service with autowired repositories.
     *
     * @param adminRepository
     */
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Checks the login credentials of the Admin
     * @param email entered email from admin login
     * @param passwd entered password from admin login
     * @return Returns the admin
     */
    public Admin getAdmin(String email, String passwd) {
        Optional<Admin> adminOptional = adminRepository.
                findAdminByEmail(email);
        if(adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            // check passwd
            if (admin.getPasswd().equals(passwd)) {
                return admin;
            } else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Returns the adminRepository
     * @return returns adminRepository
     */
    public AdminRepository getAdminRepository() {
        return adminRepository;
    }
}
