package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void println(String message);
    void print(String message);
    String readString(String message);
    int readInt(String message);
    int readInt(String message, int min, int max);
    boolean readBoolean(String message);
    BigDecimal readBigDecimal(String message);

    LocalDate readLocalDate(String message);
}
