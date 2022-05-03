package com.oliverr.scrumw.user;

import com.oliverr.scrumw.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserDataAccessService userDataAccessService;
    private final PasswordEncoder encoder;

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "new")
    public void registerUser(@RequestBody User user) {
        if(userDataAccessService.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already taken.", new RuntimeException());
        }

        if(userDataAccessService.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email is already taken.", new RuntimeException());
        }

        userDataAccessService.insertUser(user);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "login")
    public UserToken loginUser(@RequestBody User user) {
        if(userDataAccessService.findUserByUsername(user.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username does not exist.", new RuntimeException());
        }

        if(userDataAccessService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password.", new RuntimeException());
        }

        return new UserToken(
                user.getUsername(),
                userDataAccessService.getTokenByUsername(user.getUsername()).orElse("")
        );
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "token/{token}")
    public User getUserByToken(@PathVariable("token") String token) {
        return userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this token does not exist.", new RuntimeException()));
    }

    @GetMapping(path = "{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public User getUserByUsername(@PathVariable("username") String username) {
        var user = userDataAccessService
                .findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this token does not exist.", new RuntimeException()));
        user.setPassword(null);
        user.setToken(null);
        return user;
    }

}
