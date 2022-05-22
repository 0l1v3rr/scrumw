package com.oliverr.scrumw.user;

import com.oliverr.scrumw.error.ApiError;
import com.oliverr.scrumw.security.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public record UserController(UserDataAccessService userDataAccessService, PasswordEncoder encoder) {

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "new")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        if (userDataAccessService.findUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiError("This username is already taken."));
        }

        if (userDataAccessService.findUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiError("This email is already taken."));
        }

        userDataAccessService.insertUser(user);
        User createdUser = userDataAccessService.findUserByEmail(user.getEmail()).get();
        createdUser.setPassword(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        if (userDataAccessService.findUserByUsername(user.getUsername()).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("User with this username does not exist."));
        }

        if (userDataAccessService
                .findUserByUsernameAndPassword(user.getUsername(), user.getPassword())
                .isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Wrong password."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserToken(
                        user.getUsername(),
                        userDataAccessService.getTokenByUsername(user.getUsername()).get()
                ));
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "token/{token}")
    public ResponseEntity<Object> getUserByToken(@PathVariable("token") String token) {
        Optional<User> user = userDataAccessService.getUserByToken(token);

        return user.<ResponseEntity<Object>>map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(value))
                .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("User with this token does not exist."))
        );
    }

    @GetMapping(path = "{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userDataAccessService.findUserByUsername(username);

        if(user.isPresent()) {
            User presentUser = user.get();
            presentUser.setPassword(null);
            presentUser.setToken(null);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(presentUser);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("User with this username does not exist."));
    }

}
