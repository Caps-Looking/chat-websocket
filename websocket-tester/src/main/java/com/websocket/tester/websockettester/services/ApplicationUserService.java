package com.websocket.tester.websockettester.services;

import com.websocket.tester.websockettester.models.dtos.ControllerResponse;
import com.websocket.tester.websockettester.models.entities.ApplicationUser;
import com.websocket.tester.websockettester.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService extends BaseService<ApplicationUser> {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Encrypt the password and save the user
     *
     * @param user to be saved
     * @return user saved and message
     */
    public ControllerResponse save(ApplicationUser user) {
        if (user.getPassword().length() < 8) {
            return ControllerResponse.builder()
                    .message("The password needs to have at least 8 characters")
                    .data(null)
                    .messageType("danger")
                    .build();
        }
        ApplicationUser userDB = applicationUserRepository.findByUsername(user.getUsername());
        if (userDB == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return super.save(user);
        } else {
            return ControllerResponse.builder().message("User already exists").data(null).messageType("success").build();
        }
    }


    /**
     * Get user by username
     *
     * @param username that is going to be searched
     * @return the user
     */
    public ControllerResponse getUserByUsername(String username) {
        return ControllerResponse.builder()
                .message("Successfully loaded")
                .messageType("success")
                .data(applicationUserRepository.findByUsername(username))
                .build();
    }

    @Override
    public JpaRepository<ApplicationUser, Long> getRepository() {
        return applicationUserRepository;
    }
}
