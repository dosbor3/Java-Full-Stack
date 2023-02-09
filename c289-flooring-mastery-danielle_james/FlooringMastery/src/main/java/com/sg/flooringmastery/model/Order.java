package com.sg.flooringmastery.model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal cost_per_sq_foot;
    private BigDecimal labor_cost_per_sq_foot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate date;

    // TODO: Determine every other field based on the parameters in the constructor
    public Order(LocalDate date, String customerName, String state, String productType, BigDecimal area) {
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.date = date;

    }

    // Consider removing the default constructor if it's not used
    public Order() {
    }

    //Setters and Getters
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCost_per_sq_foot() {
        return cost_per_sq_foot;
    }

    public void setCost_per_sq_foot(BigDecimal cost_per_sq_foot) {
        this.cost_per_sq_foot = cost_per_sq_foot;
    }

    public BigDecimal getLabor_cost_per_sq_foot() {
        return labor_cost_per_sq_foot;
    }

    public void setLabor_cost_per_sq_foot(BigDecimal labor_cost_per_sq_foot) {
        this.labor_cost_per_sq_foot = labor_cost_per_sq_foot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", cost_per_sq_foot=" + cost_per_sq_foot +
                ", labor_cost_per_sq_foot=" + labor_cost_per_sq_foot +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(productType, order.productType) && Objects.equals(area, order.area) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, state, productType, area, date);
    }
}
