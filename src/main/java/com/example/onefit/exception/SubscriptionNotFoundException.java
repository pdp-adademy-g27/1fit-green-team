package com.example.onefit.exception;

public class SubscriptionNotFoundException extends RuntimeException{
    public SubscriptionNotFoundException(String message){
        super(message);
    }
}
