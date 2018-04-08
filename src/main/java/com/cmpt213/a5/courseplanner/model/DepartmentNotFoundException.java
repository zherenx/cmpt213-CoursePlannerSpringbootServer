package com.cmpt213.a5.courseplanner.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends IllegalArgumentException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}