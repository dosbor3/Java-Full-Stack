package com.sg.flooringmastery.service;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
