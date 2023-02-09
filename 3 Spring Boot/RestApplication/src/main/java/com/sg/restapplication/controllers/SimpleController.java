package com.sg.restapplication.controllers;

/*
GetMapping, RequestMapping, and RestController
are Spring MVC types that come along with the spring-boot-starter-web dependency.
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//The annotation, @RestController, notifies Spring MVC that this class should be registered
//with the Spring application context and that it may contain methods that handle REST requests.
@RestController

/*
@RequestMapping("/api") is our first mapping annotation. Mapping (or Routing), determines if a given URL,
HTTP method, HTTP header, or media type triggers a specific controller method. By applying this annotation at
the class level, we tell Spring MVC that this class can only handle URLs that begin with "/api". We don't have
to map requests at the class level, but it's convenient. We can also map requests method by method.
 */
@RequestMapping("/api")
public class SimpleController {

    /*
    The @GetMapping signals to Spring MVC that this method can only handle HTTP requests using the GET method.
    It can, but doesn't, further refine the accepted URL.
     */
    @GetMapping
    public String[] helloWorld() {
        //The method helloWorld returns a String[] to the Spring MVC framework, which then serializes the
        //result to JSON and includes it in the HTTP response body.
        String[] result = {"Hello", "World", "!"};
        return result;
    }

    /*
    @PostMapping("/calculate") tells Spring MVC to execute our method if an HTTP request's method
    is POST and the URL is "/api/calculate". The @PostMapping's relative URL is appended to the
    @RequestMapping's URL at the top of the class.
     */
    @PostMapping("/calculate")
    /*
    Our method returns a String. Compare that to helloWorld's String[]. @RestController methods are
    remarkably flexible. Spring MVC will take any type returned and do its best to format it as JSON.
     */
    public String calculate(int operand1, String operator, int operand2) {
        int result = 0;
        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            default:
                String message = String.format("operator '%s' is invalid", operator);
                throw new IllegalArgumentException(message);
        }
        return String.format("%s %s %s = %s", operand1, operator, operand2, result);
    }

    /*
    @DeleteMapping("/resource/{id}") tells Spring MVC to call our method when the HTTP method is DELETE.
    Its URL has a named chunk delimited with curly braces. It represents a variable chunk. Its value can
    be almost anything other than "/" and will match a URL as long as the rest of the URL matches.
     */
    @DeleteMapping("/resource/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT) overrides the default and returns a 204 No Content status
    //for every request. If we want something else, this approach would be too rigid.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PathVariable tells Spring MVC to find the parameter in the URL. In this case, it's
    // the variable chunk {id}. The parameter's name must match the chunk's name.
    public void delete(@PathVariable int id) {
        //Our method doesn't return a type. It's void. That's fine with Spring MVC. It omits the HTTP response
        // body and returns 200 OK by default.

        // This is where we would use our id to delete.
    }
}
