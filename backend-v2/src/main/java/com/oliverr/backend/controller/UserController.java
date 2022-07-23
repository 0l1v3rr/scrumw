package com.oliverr.backend.controller;

import com.oliverr.backend.model.User;
import com.oliverr.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User saved = userService.saveUser(user);
        user.setPassword(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findUser(username);
        user.setPassword(null);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
