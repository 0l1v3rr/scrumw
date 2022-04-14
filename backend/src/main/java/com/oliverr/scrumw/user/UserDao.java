package com.oliverr.scrumw.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectUsers();
    int insertUser(User user);
    int deleteUser(int id);
    Optional<User> selectUserById(int id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
