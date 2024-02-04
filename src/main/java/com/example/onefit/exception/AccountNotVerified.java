package com.example.onefit.exception;


public class AccountNotVerified extends RuntimeException{

    public AccountNotVerified(String message){
        super(message);
    }
}
