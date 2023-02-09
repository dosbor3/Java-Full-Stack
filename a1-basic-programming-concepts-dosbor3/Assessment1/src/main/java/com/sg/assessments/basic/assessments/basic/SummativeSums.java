package com.sg.assessments.basic.assessments.basic;

public class SummativeSums {
    public static void main(String[] args) {
        int array_one_sum;
        int array_two_sum;
        int array_three_sum;

        int[] array1 = { 1, 90, -33, -55, 67, -16, 28, -55, 15 };
        int[] array2 = { 999, -60, -77, 14, 160, 301 };
        int[] array3 = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
                140, 150, 160, 170, 180, 190, 200, -99 };

        array_one_sum = sumArray(array1);
        array_two_sum = sumArray(array2);
        array_three_sum = sumArray(array3);

        System.out.println("#1 Array Sum: " + array_one_sum);
        System.out.println("#2 Array Sum: " + array_two_sum);
        System.out.println("#3 Array Sum: " + array_three_sum);


    }

    public static int sumArray(int[] some_array) {
        int sum = 0;
        for(int i = 0; i < some_array.length; i++) {
            sum += some_array[i];
        };
        return sum;
    }
}
