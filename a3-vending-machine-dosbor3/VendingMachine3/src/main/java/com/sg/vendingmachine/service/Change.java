package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dto.Currency;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Change {
    //this method will take in the enum Currency and funds and change amount
    //based on currency the change will be calculated to return change minus the currency calculated

    /*
    Thinkink of change class taking in buyer funds and item cost as parameters... if so
    call this class and method in the service layer method getChange
     */
    public static void calculateChange(String item_cost, String buyer_funds) {
        com.sg.vendingmachine.dto.Currency coin = com.sg.vendingmachine.dto.Currency.QUARTER;
        BigDecimal bd_item_cost = new BigDecimal(item_cost);
        BigDecimal bd_buyer_funds = new BigDecimal(buyer_funds);
        BigDecimal bd_change_value = bd_buyer_funds.subtract(bd_item_cost);
        String string_chang_value = bd_change_value.toString();

        System.out.println("\nCalculating coin change.......");
        System.out.println("Change Due: $" + string_chang_value + "\n");


        bd_change_value = bd_change_value.remainder(new BigDecimal("1"));
        System.out.println("||--||  Coins returned: ||--||");

        switch(coin) {
            case QUARTER: {
                BigDecimal bd_quarter_value = new BigDecimal(".25");
                BigInteger bd_quarter_count = bd_change_value.divide(bd_quarter_value).toBigInteger();
                BigDecimal bi_quarter_count_to_bd = new BigDecimal(bd_quarter_count);
                BigDecimal quarter_calculation = bi_quarter_count_to_bd.multiply(bd_quarter_value);

                bd_change_value = bd_change_value.subtract(quarter_calculation);
                if (bd_quarter_count.intValue() > 1) {
                    System.out.println("Quarters: " + bd_quarter_count.toString());
                } else {
                    System.out.println("Quarter: " + bd_quarter_count.toString());
                }
                coin = com.sg.vendingmachine.dto.Currency.DIME;//TODO MOVE to VIEW...
            }
            case DIME: {
                //TODO dimes for not equal to 1
                BigDecimal bd_dime_value = new BigDecimal(".10");
                BigInteger bd_dime_count = bd_change_value.divide(bd_dime_value).toBigInteger();//adjust rounding mode...
                BigDecimal bi_dime_count_to_bd = new BigDecimal(bd_dime_count);
                BigDecimal dime_calculation = bi_dime_count_to_bd.multiply(bd_dime_value);
                bd_change_value = bd_change_value.subtract(dime_calculation);

                if (bd_dime_count.intValue() > 1) {
                    System.out.println("Dimes: " + bd_dime_count.toString());
                } else {
                    System.out.println("Dime: " + bd_dime_count.toString());
                }

                coin = com.sg.vendingmachine.dto.Currency.NICKLE;
            }
            case NICKLE: {
                BigDecimal bd_nickle_value = new BigDecimal(".05");
                BigInteger bd_nickle_count = bd_change_value.divide(bd_nickle_value).toBigInteger();
                BigDecimal bi_nickle_count_to_bd = new BigDecimal(bd_nickle_count);
                BigDecimal nickle_calculation = bi_nickle_count_to_bd.multiply(bd_nickle_value);
                bd_change_value = bd_change_value.subtract(nickle_calculation);
                if (bd_nickle_count.intValue() > 1) {
                    System.out.println("Nickles: " + bd_nickle_count.toString());
                } else {
                    System.out.println("Nickle: " + bd_nickle_count.toString());
                }

                coin = Currency.PENNY;
            }
            case PENNY: {
                BigDecimal bd_penny_value = new BigDecimal(".01");
                BigInteger bd_penny_count = bd_change_value.divide(bd_penny_value).toBigInteger();
                BigDecimal bi_penny_count_to_bd = new BigDecimal(bd_penny_count);
                BigDecimal penny_calculation = bi_penny_count_to_bd.multiply(bd_penny_value);
                bd_change_value = bd_change_value.subtract(penny_calculation);
                if (bd_penny_count.intValue() > 1) {
                    System.out.println("Pennies: " + bd_penny_count.toString());
                } else {
                    System.out.println("Penny: " + bd_penny_count.toString());
                }
            }
            default:
                break;
        }
    }
}
