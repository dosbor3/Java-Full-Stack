package com.sg.flooringmastery.dao;
import com.sg.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.*;

public interface FlooringMasteryOrderDao {
    Order addOrder(Order order, LocalDate day);
    List<Order> getOrderOnDay(LocalDate date);
    Order removeOrder(LocalDate date, int orderNo);
    Order editOrder(LocalDate oldDate, Order newOrder);
    Order retrieveOrder(LocalDate date, int orderNo);
    boolean checkDateExists(LocalDate date);
    boolean checkOrderExists(LocalDate date, int orderNo);
    int getTotalOrder();
    int loadAllDayOrders() throws FlooringMasteryPersistenceException; // returns total number of orders loaded
    int importOrders();
    int writeAllDayOrders() throws FlooringMasteryPersistenceException; // returns total number of orders loaded
    int exportOrders() throws FlooringMasteryPersistenceException; // write all order into same file


    /* TODO
    read state file product type file--has been completed in FlooringMasteryProductDaoFileImpl class
    int getDayBeginId(LocalDate date);
    int getDayNumOrder(LocalDate date);
     */
}
