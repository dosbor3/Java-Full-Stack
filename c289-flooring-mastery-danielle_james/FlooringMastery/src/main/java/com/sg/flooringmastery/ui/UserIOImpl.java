package com.sg.flooringmastery.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class UserIOImpl implements UserIO{
    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String message) {
        Scanner input = new Scanner(System.in);
        this.println(message);
        return input.nextLine();
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = readInt(prompt);
        } while (result < min || result > max);
        return result;
    }
    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }
    // We're going to assume the user will either answer "Yes"/"y" or "No"/'n" or some sort of variant of these
    // Considering changing this later to make it to handle invalid answers
    @Override
    public boolean readBoolean(String message) {
        return (message.contains("y") || message.contains("Y") ? true : false);
    }

    @Override
    public BigDecimal readBigDecimal(String message) {
        BigDecimal num = BigDecimal.valueOf(0);
        boolean invalidInput = true;
        do {
            try {
                String strValue = this.readString(message);
                num = new BigDecimal(strValue);
                invalidInput = false;
            } catch (NumberFormatException ex) {
                this.print("Input error, please try again.");
            }
        } while (invalidInput);
        return num;
    }
    @Override
    public LocalDate readLocalDate(String prompt) {
        LocalDate date = LocalDate.now();
        boolean invalidInput = true;
        do {
            try {
                String strValue = this.readString(prompt);
                date = LocalDate.parse(strValue, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                invalidInput = false;
            } catch (DateTimeParseException e) {
                this.print("Please make sure date is of format MM-dd-yyyy");
            }
        } while (invalidInput);
        return date;
    }}
