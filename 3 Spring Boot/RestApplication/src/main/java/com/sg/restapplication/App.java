package com.sg.restapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Kicks off a component scan starting in the annotated class's package
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}