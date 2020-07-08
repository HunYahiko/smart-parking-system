package com.example.smartparkapp.authentication.exceptions;

public class LoginException extends Exception {

    private String context;

    public LoginException() {
    }

    public LoginException(String message, String context) {
        super(message);
        this.context = context;
    }

    public String getContext() {
        return context;
    }
}
