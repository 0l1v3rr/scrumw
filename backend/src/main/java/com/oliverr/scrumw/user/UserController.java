package com.oliverr.scrumw.user;

import com.oliverr.scrumw.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username is already taken.", new RuntimeException());
        }

        if(userDataAccessService.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already taken.", new RuntimeException());
        }

        userDataAccessService.insertUser(user);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "login")
    public UserToken loginUser(@RequestBody User user) {
        if(userDataAccessService.findUserByUsername(user.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member with this username does not exist.", new RuntimeException());
        }

        if(userDataAccessService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password.", new RuntimeException());
        }

        return new UserToken(
                user.getUsername(),
                userDataAccessService.getTokenByUsername(user.getUsername()).orElse("")
        );
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "token/{token}")
    public User getUserByToken(@PathVariable("token") String token) {
        return userDataAccessService.getUserByToken(token).orElse(new User());
    }

}
