package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

public class Buyer {
    private String funds;
    private BigDecimal change;

    public Buyer(String funds) {
        this.funds = funds;
    }

    public void setFunds(String funds) {this.funds = funds;}
    public String getFunds() {return this.funds;}
    public void setChange(BigDecimal change) {this.change = change;}
    public BigDecimal getChange() {return this.change;}
}
