package com.oliverr.scrumw.security;

import com.oliverr.scrumw.user.User;
import com.oliverr.scrumw.user.UserDataAccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public record AuthenticateUser(UserDataAccessService userDataAccessService) {

    public boolean withToken(String token, String expectedUsername) {
        if (token == null) {
            return false;
        }

        Optional<User> userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            return false;
        }

        if (!userByToken.get().getUsername().equalsIgnoreCase(expectedUsername)) {
            return false;
        }

        return true;
    }

}
