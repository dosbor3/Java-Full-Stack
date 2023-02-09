package com.sg.todoapi.controllers;
/*
First, create a class to hold our custom error message. It doesn't need to extend Exception.
It's a simple container for transmitting error data. Put Error in [base-package].controllers.
There are arguably better places for it, but since we will only use it in a controller, controllers is a good simple first home.
 */
import java.time.LocalDateTime;

public class Error {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
