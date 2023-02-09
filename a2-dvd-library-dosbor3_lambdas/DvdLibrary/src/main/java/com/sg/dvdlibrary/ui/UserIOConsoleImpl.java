package com.sg.dvdlibrary.ui;

import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class UserIOConsoleImpl implements UserIO{
    public UserIOConsoleImpl() {

    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        String response = input.nextLine();
        return response;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        int response = Integer.parseInt(input.nextLine());
        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int response;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println(prompt);
            response = Integer.parseInt(input.nextLine());

        } while(response < min || response > max);

        return response;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        double response = Double.parseDouble(input.nextLine());
        return response;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double response;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println(prompt);
            response = Double.parseDouble(input.nextLine());

        } while(response < min || response > max);

        return response;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        float response = Float.parseFloat(input.nextLine());
        return response;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float response;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println(prompt);
            response = Float.parseFloat(input.nextLine());

        } while(response < min || response > max);

        return response;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        Long response = Long.parseLong(input.nextLine());
        return response;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        Long response;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println(prompt);
            response = Long.parseLong(input.nextLine());

        } while(response < min || response > max);

        return response;
    }
}
