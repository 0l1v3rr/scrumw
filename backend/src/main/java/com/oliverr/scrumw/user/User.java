package com.oliverr.scrumw.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String token;
    private LocalDate registrationDate;

    public User(String username, String email, String password, String token, LocalDate registrationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.registrationDate = registrationDate;
    }

}
