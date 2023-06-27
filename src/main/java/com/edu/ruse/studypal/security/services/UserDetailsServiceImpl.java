package com.edu.ruse.studypal.security.services;

import com.edu.ruse.studypal.controllers.FacultiesController;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(FacultiesController.class);

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public User getUserById(long id) {
        return getUserEntity(id).get();
    }

    private Optional<User> getUserEntity(long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            LOGGER.error("User with id {} not found", id);
            throw new EntityNotFoundException("Faculty not found");
        }

        return userOptional;
    }
}
