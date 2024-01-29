package com.example.onefit.exception.exceptionHandling;

import com.example.onefit.exception.DataNotFoundException;
import com.example.onefit.exception.IncorrectPassword;
import com.example.onefit.exception.TimeOut;
import com.example.onefit.exception.TokenExpiredException;
import com.example.onefit.exception.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<String> handleException(IncorrectPassword e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeOut.class)
    public ResponseEntity<String> handleException(TimeOut e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionDto> dataNotFound(DataNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(e.getMessage(), 404));
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionDto> tokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(e.getMessage(), 404));
    }


}
