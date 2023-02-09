package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.State;
import com.sg.flooringmastery.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {
    void createOrder(Order order, LocalDate orderDate) throws InvalidDateException, InvalidNameException;
    Order removeOrder(int orderNumber, LocalDate orderDate) throws InvalidDateException, OrderNotFoundException;
    Order validateOrder(Order newOrder) throws FlooringMasteryPersistenceException,
            InvalidDateException, ValidationFloorAreaException, InvalidNameException;
    void editOrder(LocalDate oldDate, Order newOrder) throws InvalidDateException, OrderNotFoundException;

    List<Order> getOrdersByDate(LocalDate orderDate) throws InvalidDateException;
    Order retrieveOrder(LocalDate orderDate, int orderNo) throws OrderNotFoundException, InvalidDateException;

    List<State> loadStates() throws FlooringMasteryPersistenceException;
    List<String> getStateAbbrev() throws FlooringMasteryPersistenceException;
    List<Product> loadProducts() throws FlooringMasteryPersistenceException;
    List<String> loadProductsType() throws FlooringMasteryPersistenceException;
    int loadAllOrders() throws FlooringMasteryPersistenceException;
    int exportOrders() throws FlooringMasteryPersistenceException;
    int writeAllOrders() throws FlooringMasteryPersistenceException;
    BigDecimal calculateCostsTotal(Order order) ;
    BigDecimal calculateMaterialCost(Order order) ;
    BigDecimal calculateLaborCost(Order order) ;
    BigDecimal calculateTax(Order order) ;
    Boolean validateOrderDate(LocalDate orderDate) throws InvalidDateException;
    Boolean validateCustomerName(String inputName ) throws InvalidNameException;
    Boolean validateFloorArea(BigDecimal area) throws ValidationFloorAreaException;

   // Boolean validateProductType (String inputProduct);
    }
