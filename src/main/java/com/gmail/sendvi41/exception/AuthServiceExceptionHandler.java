package com.gmail.sendvi41.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sendvi41.dto.ErrorDto;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class AuthServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    public ObjectMapper mapper;

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ErrorDto> handleException(BaseException ex, WebRequest request) {
        String message = ex.getMessage();
        log.warn(message);
        return ResponseEntity.status(ex.getHttpStatus()).body(ErrorDto.of(message));
    }
}