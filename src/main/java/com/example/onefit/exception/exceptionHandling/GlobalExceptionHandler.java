package com.example.onefit.exception.exceptionHandling;

import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.exception.DataNotFoundException;
import com.example.onefit.exception.IncorrectPassword;
import com.example.onefit.exception.TimeOut;
import com.example.onefit.exception.TokenExpiredException;
import com.example.onefit.exception.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<String> handleException(IncorrectPassword e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeOut.class)
    public ResponseEntity<String> handleException(TimeOut e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleDataNotFound(DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(e.getMessage(), 404));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionDto> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(e.getMessage(), 401));
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<String> handleException(RedisConnectionFailureException e) {
        log.error(ExcMessage.REDIS_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(e.getMessage(), 500));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleEntityNotFound(EntityNotFoundException e) {
        log.error(ExcMessage.ENTITY_NOTFOUND + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(e.getMessage(), 404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUsernameNotFound(UsernameNotFoundException e) {
        log.error(ExcMessage.USERNAME_NOTFOUND + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(e.getMessage(), 404));
    }


}