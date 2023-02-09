package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {
    private Map<LocalDate, List<Order>> allOrdersByDate = new HashMap<>();

    @Override
    public Order addOrder(Order order, LocalDate day) {
        if (!allOrdersByDate.containsKey(day)) {
            allOrdersByDate.put(day, new ArrayList<>());
        }
        order.setOrderNumber(allOrdersByDate.get(day).size());
        allOrdersByDate.get(day).add(order);
        return order;
    }

    @Override
    public List<Order> getOrderOnDay(LocalDate date) {
        return allOrdersByDate.get(date);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNo) {
        return allOrdersByDate.get(date).remove(orderNo);
    }

    @Override
    public Order editOrder(LocalDate oldDate, Order newOrder) {
        LocalDate newDate = newOrder.getDate();
        int orderNo = newOrder.getOrderNumber();
        if (oldDate.isEqual(newDate)) {
            return allOrdersByDate.get(oldDate).set(orderNo, newOrder);
        } else if (!checkDateExists(newDate)) {
            allOrdersByDate.put(newDate, new ArrayList<>());
        }
        removeOrder(oldDate, orderNo);
        return addOrder(newOrder, newDate);
    }

    @Override
    public Order retrieveOrder(LocalDate date, int orderNo) {
        return allOrdersByDate.get(date).get(orderNo);
    }

    @Override
    public boolean checkDateExists(LocalDate date) {
        return allOrdersByDate.containsKey(date);
    }

    @Override
    public boolean checkOrderExists(LocalDate date, int orderNo) {
        return checkDateExists(date) && allOrdersByDate.get(date).size() > orderNo;
    }

    @Override
    public int getTotalOrder() {
        return allOrdersByDate.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    @Override
    public int loadAllDayOrders() throws FlooringMasteryPersistenceException {
        return getTotalOrder();
    }

    @Override
    public int importOrders() {
        return getTotalOrder();
    }

    @Override
    public int writeAllDayOrders() throws FlooringMasteryPersistenceException {
        return getTotalOrder();
    }

    @Override
    public int exportOrders() throws FlooringMasteryPersistenceException {
        return getTotalOrder();
    }
}
