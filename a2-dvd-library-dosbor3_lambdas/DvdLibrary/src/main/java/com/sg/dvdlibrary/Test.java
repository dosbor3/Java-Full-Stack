package com.sg.dvdlibrary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        String[] date = {"1957-07-24", "2003-11-20", "2002-07-16"};

        LocalDate ld_release_date = LocalDate.parse(date[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(ld_release_date.getYear());
        System.out.println(ld_release_date);


        int current_year = LocalDate.now().getYear();
        int year_range = current_year - 7;

        //LocalDate year_range = current_year.minusYears(Integer.parseInt("7"));

        System.out.println(year_range);
    }
}
