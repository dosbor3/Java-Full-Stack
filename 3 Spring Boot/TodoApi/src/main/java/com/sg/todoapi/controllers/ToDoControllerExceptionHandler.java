package com.sg.todoapi.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice   //indicates our class will assist all controllers in our project.
@RestController     //indicates our class is able to serve an HTTP response on behalf of a controller.
public class ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler {
    /*
    ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler -- The ResponseEntityExceptionHandler
    class contains a bunch of exception handling code, so we get it for free by extending.
     */

    private static final String CONSTRAINT_MESSAGE = "Could not save your item. "
            + "Please ensure it is valid and try again.";

    /*
    Then we add one or more methods. Each method must accept a Java exception and WebRequest as
    parameters and return a ResponseEntity<T>.

    In addition, we have to annotate each method to indicate which exception it's handling:
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class).

    Inside the method, we're free to construct our ResponseEntity in any way we see fit. In our case,
    we return an instance of Error inside and anonymize our exception details.
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}