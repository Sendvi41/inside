package com.gmail.sendvi41.exception;

import org.springframework.http.HttpStatus;

public class IncorrectPatternForMessage extends BaseException{
    public IncorrectPatternForMessage(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
