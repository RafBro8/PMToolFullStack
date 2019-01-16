package com.alpha.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ProjectIdException extends RuntimeException {

    public ProjectIdException(String message) {
        super(message);
    }
}

//String message and super(message); are coming from parent Object RuntimeException

//This ProjectIdException will be passed to CustomResponseEntityExceptionHandler
