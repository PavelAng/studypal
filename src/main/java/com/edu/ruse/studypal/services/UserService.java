package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.entities.User;

public interface UserService {
    User findByEmail(String email);
}

