package com.oliverr.scrumw.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    private String username;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
