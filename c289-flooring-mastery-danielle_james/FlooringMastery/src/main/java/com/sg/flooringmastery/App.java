package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryOrderController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg");
        appContext.refresh();

        FlooringMasteryOrderController controller = appContext.getBean("flooringMasteryOrderController", FlooringMasteryOrderController.class);
        controller.run();
    }

}
