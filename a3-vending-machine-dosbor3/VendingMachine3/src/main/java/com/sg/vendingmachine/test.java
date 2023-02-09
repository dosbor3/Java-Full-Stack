package com.sg.vendingmachine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class test {
    public static void main(String[] args) {
        String coin_balance = "";
        //double double_change_value = Double.parseDouble(change);
        double double_change_value = .87;
        double new_change_value = 0;
        int quarters = 0;
        int dimes = 0;
        int nickles = 0;
        int pennies = 0;

        quarters = (int) (double_change_value / .25);
        if(quarters > 0) {
            new_change_value  = double_change_value - (quarters * .25);
        }


        //Big Decimal Calculations
        BigDecimal bd_change_value = new BigDecimal("2.62");
        BigDecimal bd_new_change_value = new BigDecimal(("0"));
        BigInteger bd_int_quarters = bd_change_value.divide(new BigDecimal(".25")).toBigInteger();
        BigDecimal bd_int_dimes = new BigDecimal("0");
        BigDecimal bd_int_nickles = new BigDecimal("0");
        BigDecimal bd_int_pennies = new BigDecimal("0");
        bd_change_value.divide(new BigDecimal(".25"));
        BigDecimal bd_only_change = bd_change_value.remainder(new BigDecimal("1"));
        System.out.println(bd_only_change);
        //System.out.println(bd_int_quarters);



        //System.out.println(quarters);
        //System.out.println(new_change_value);

    }
}
