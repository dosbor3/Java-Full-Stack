package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String stateAbbrv;
    private String stateFullname;
    private BigDecimal taxRate;


    public State(String stateAbbr, String stateFullname, BigDecimal taxRate) {
        this.stateAbbrv = stateAbbr;
        this.stateFullname = stateFullname;
        this.taxRate = taxRate;
    }

    public State(String stateAbbrv) {
        this.stateAbbrv = stateAbbrv;
    }

    public String getStateFullname() {
        return stateFullname;
    }

    public void setStateFullname(String stateFullname) {
        this.stateFullname = stateFullname;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getStateAbbrv() {
        return stateAbbrv;
    }

    public void setStateAbbrv(String stateAbbrv) {
        this.stateAbbrv = stateAbbrv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(stateAbbrv, state.stateAbbrv) && Objects.equals(stateFullname, state.stateFullname) && Objects.equals(taxRate, state.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbbrv, stateFullname, taxRate);
    }
}
