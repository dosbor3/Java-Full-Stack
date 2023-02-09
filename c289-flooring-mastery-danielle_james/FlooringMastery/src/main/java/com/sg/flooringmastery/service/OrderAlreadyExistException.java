package com.sg.flooringmastery.service;

public class OrderAlreadyExistException extends Exception {
    public OrderAlreadyExistException(String message) {
        super(message);
    }

    public OrderAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
