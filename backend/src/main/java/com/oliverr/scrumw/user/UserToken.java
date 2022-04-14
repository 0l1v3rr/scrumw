package com.oliverr.scrumw.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserToken {

    private String username;
    private String token;

}
