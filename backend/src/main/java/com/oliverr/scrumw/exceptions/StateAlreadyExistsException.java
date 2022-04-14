package com.oliverr.scrumw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StateAlreadyExistsException extends RuntimeException {

    public StateAlreadyExistsException(String message) {
        super(message);
    }

    public StateAlreadyExistsException() {
        super();
    }

}
