package com.oliverr.scrumw.user;

import com.oliverr.scrumw.exceptions.StateAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserDataAccessService userDataAccessService;

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(path = "new")
    public void registerUser(@RequestBody User user) {
        if(userDataAccessService.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username is already taken.", new StateAlreadyExistsException());
        }

        if(userDataAccessService.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already taken.", new StateAlreadyExistsException());
        }

        userDataAccessService.insertUser(user);
    }

}
