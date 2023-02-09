package com.sg.assessments.basic.assessments.basic;

import java.util.Scanner;

public class HealthyHearts {
    static Scanner input = new Scanner(System.in);
    static int age;
    static int max_heart_rate;
    static int target_heart_rate_zone_min;
    static int target_heart_rate_zone_max;


    public static void main(String[] args) {
        getUserAge();
        calculateMaximumHeartRate();
        calculateTargetHRZone();

    }

    public static void getUserAge() {
        System.out.print("What is your age? ");
        age = input.nextInt();
    }

    public static void calculateMaximumHeartRate() {
        max_heart_rate = 220 - age;
        System.out.println("Your maximum heart rate should be " + max_heart_rate + " beats per minute");
    }

    public static void calculateTargetHRZone() {
        target_heart_rate_zone_min = (int) Math.ceil(max_heart_rate * .50);
        target_heart_rate_zone_max = (int) Math.ceil(max_heart_rate * .85);
        System.out.println("Your target HR Zone is " + target_heart_rate_zone_min + " - " + target_heart_rate_zone_max + " beats per minute");
    }
}
