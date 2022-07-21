package com.oliverr.backend.service;

import com.oliverr.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUser(Long id);
    User findUser(String username);
    List<User> getUsers();
    User saveUser(User user);
}
