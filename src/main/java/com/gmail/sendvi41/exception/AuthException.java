package com.gmail.sendvi41.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException{
    public AuthException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
