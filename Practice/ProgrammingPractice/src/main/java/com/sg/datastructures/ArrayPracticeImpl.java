package com.sg.datastructures;


import org.springframework.stereotype.Component;

@Component("arrayBean")
public class ArrayPracticeImpl implements ArrayPractice {
    @Override
    public int[] createArray() {
        int max = 99;
        int min = 1;
        int rand = 0;

        int[] numbers = new int[10];

        for(int i = 0; i < 10; i++) {
            rand = (int)(Math.random() * max - min) + min;
            numbers[i] = rand;
        }
        return numbers;
    }

    @Override
    public int[] deleteArrayElement(int index, int[] someArray) {
        int[] newArray = new int[someArray.length -1];

        for(int i = 0, j = 0; i < newArray.length; i++,j++) {
            if( i == index) {
                newArray[i] = someArray[++j];
            }
            else {
                newArray[i] = someArray[j];
            }
        }

        return newArray;
    }

    @Override
    public void copyArray(int[] someArray) {
        int[] copy = new int[someArray.length];
        for(int i : copy) {
            copy[i] = someArray[i];
            System.out.println(copy[i] + "\t" + someArray[i]);
        }
    }

    @Override
    public void shiftArrayElements(int[] someArray) {
        int[] shiftedArray = new int[someArray.length];

        int last_element = someArray[someArray.length-1];

        for(int i = 0, j = 0; i < shiftedArray.length; i++) {
            if( i == 0 ){
                shiftedArray[i] = last_element;
            }
            else {
                shiftedArray[i] = someArray[j];
                j++;
            }
        }

        for(int o : someArray) {
            System.out.print(o + "\t");
        }

        System.out.println();

        for(int s : shiftedArray) {
            System.out.print(s + "\t");
        }


    }
}
