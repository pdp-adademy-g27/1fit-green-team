package com.example.onefit.exception.exceptionHandling;

import com.example.onefit.common.variable.ExcMessage;
import com.example.onefit.exception.*;
import com.example.onefit.exception.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.onefit.common.variable.ExcMessage.*;


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


    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleSubscriptionNotFound(SubscriptionNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(e.getMessage(), 404));
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleBadCredentials(BadCredentialsException e) {
        log.error(ExcMessage.BAD_CREDENTIALS + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(e.getMessage(), 401));
    }

    @ExceptionHandler(NotAllowedFemaleException.class)
    public ResponseEntity<ExceptionDto> handleNotAllowed(NotAllowedFemaleException e){
        log.error(NOT_ALLOWED_FEMALE + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(e.getMessage(), 400));
    }


    @ExceptionHandler(NotAllowedMaleException.class)
    public ResponseEntity<ExceptionDto> handleNotAllowed(NotAllowedMaleException e){
        log.error(NOT_ALLOWED_MALE + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(e.getMessage(), 400));
    }

    @ExceptionHandler(AccountNotVerified.class)
    public ResponseEntity<ExceptionDto> handleNotAllowed(AccountNotVerified e){
        log.error(ACCOUNT_NOT_VERIFIED + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(e.getMessage(), 401));
    }



}
