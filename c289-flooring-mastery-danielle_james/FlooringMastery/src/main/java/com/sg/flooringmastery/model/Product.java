package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String productType;
    private BigDecimal cost_per_sq_foot;
    private BigDecimal labor_cost_per_sq_foot;


    public Product(String productType, BigDecimal sqFootCost, BigDecimal laborCost) {
        this.productType = productType;
        this.cost_per_sq_foot = sqFootCost;
        this.labor_cost_per_sq_foot = laborCost;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) && Objects.equals(cost_per_sq_foot, product.cost_per_sq_foot) && Objects.equals(labor_cost_per_sq_foot, product.labor_cost_per_sq_foot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, cost_per_sq_foot, labor_cost_per_sq_foot);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", cost_per_sq_foot=" + cost_per_sq_foot +
                ", labor_cost_per_sq_foot=" + labor_cost_per_sq_foot +
                '}';
    }
}
