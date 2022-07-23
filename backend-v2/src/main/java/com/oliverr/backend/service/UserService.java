package com.oliverr.backend.service;

import com.oliverr.backend.model.User;

public interface UserService {
    User findUser(Long id);
    User findUser(String username);
    User saveUser(User user);
}
