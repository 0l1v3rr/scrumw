package com.oliverr.scrumw.util;

import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class TokenGenerator {

    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateToken(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(r.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
