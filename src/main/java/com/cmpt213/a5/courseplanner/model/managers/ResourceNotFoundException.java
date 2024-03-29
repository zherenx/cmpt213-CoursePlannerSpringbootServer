package com.cmpt213.a5.courseplanner.model.managers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends IllegalArgumentException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
