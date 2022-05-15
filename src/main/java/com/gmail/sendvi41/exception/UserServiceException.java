package com.gmail.sendvi41.exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends BaseException{
    public UserServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
