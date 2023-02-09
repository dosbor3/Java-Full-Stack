package com.sg;

import com.sg.datastructures.ArrayPractice;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        int sum = 0;

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg");
        appContext.refresh();

        ArrayPractice array = (ArrayPractice) appContext.getBean("arrayBean");
        int[] someNumberArray = array.createArray();
        System.out.println("this is practice for arrays and Spring Dependency Injection (DI)");
        for(int i : someNumberArray) {
            sum += i;
            System.out.println(i);
        }
        System.out.println("Total: " + sum);

        int[] deletedIndex = array.deleteArrayElement(5, someNumberArray);
        for(int j : deletedIndex) {
            System.out.println(j);
        }

        System.out.println("The shifted array: \n");
        array.shiftArrayElements(deletedIndex);
    }

}
