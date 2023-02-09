package com.sg.vendingmachine.service;

import java.math.BigDecimal;

public class Change {
    //this method will take in the enum Currency and funds and change amount
    //based on currency the change will be calculated to return change minus the currency calculated
    public BigDecimal calculateChange(Currency coin, String change) {
        String coin_balance = "";
        switch(coin) {
            case QUARTER: {
                System.out.println("Case for Quarter");
            }
            case DIME: {
                System.out.println("Case for Dime");
            }
            case NICKLE: {
                System.out.println("Case for Nickle");
            }
            case PENNY: {
                System.out.println("Case for Penny");
            }
            default:
                throw new UnsupportedOperationException();
        }
    }

}
