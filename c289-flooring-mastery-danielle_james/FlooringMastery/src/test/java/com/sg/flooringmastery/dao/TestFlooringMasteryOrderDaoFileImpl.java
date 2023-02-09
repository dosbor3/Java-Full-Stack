package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestFlooringMasteryOrderDaoFileImpl {
    public FlooringMasteryOrderDao dao;
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate A_WEEK_FROM_NOW = LocalDate.now().plusWeeks(1);

    private List<Order> ordersTomorrow;
    private List<Order> ordersAWeekFromNow;

    @BeforeEach
    public void setUp() {
        dao = new FlooringMasteryOrderDaoFileImpl();
        dao.addOrder(new Order(TOMORROW, "John Smith", "AL", "Carpet", new BigDecimal("123")), TOMORROW);
        dao.addOrder(new Order(TOMORROW, "Bob Jones", "FL", "Wood", new BigDecimal("321")), TOMORROW);
        dao.addOrder(new Order(A_WEEK_FROM_NOW, "James Bond", "GA", "Marble", new BigDecimal("999")), A_WEEK_FROM_NOW);

        ordersTomorrow = dao.getOrderOnDay(TOMORROW);
        ordersAWeekFromNow = dao.getOrderOnDay(A_WEEK_FROM_NOW);
    }

    @Test
    public void testGetTotalOrder() {
        Assertions.assertEquals(3, dao.getTotalOrder());

        dao.addOrder(new Order(A_WEEK_FROM_NOW, "Johnny Test", "California", "Wood", new BigDecimal("2323")), A_WEEK_FROM_NOW);
        Assertions.assertEquals(4, dao.getTotalOrder());
    }

    @Test
    public void testGetOrderOnDay() {
        Order firstOrderTomorrow = new Order(TOMORROW, "John Smith", "AL", "Carpet", new BigDecimal("123"));
        Order secondOrderTomorrow = new Order(TOMORROW, "Bob Jones","FL", "Wood", new BigDecimal("321"));
        Order orderWeekFromNow = new Order(A_WEEK_FROM_NOW, "James Bond", "GA", "Marble", new BigDecimal("999"));

        Assertions.assertTrue(ordersTomorrow.get(0).equals(firstOrderTomorrow));
        Assertions.assertTrue(ordersTomorrow.get(1).equals(secondOrderTomorrow));
        Assertions.assertEquals(ordersTomorrow.size(), 2);

        Assertions.assertTrue(ordersAWeekFromNow.get(0).equals(orderWeekFromNow));
        Assertions.assertEquals(ordersAWeekFromNow.size(), 1);
    }

    @Test
    public void testRemoveOrder() {
        dao.removeOrder(TOMORROW, 2);
        Assertions.assertNull(dao.checkOrderExists(TOMORROW, 2));
        Assertions.assertEquals(1, ordersTomorrow);
    }

    @Test
    public void testEditOrder() {
        Order newOrder = new Order(TOMORROW, "Bob Billy-Bob", "CA", "Marble", new BigDecimal("555"));
        newOrder.setOrderNumber(2);
        dao.editOrder(TOMORROW, newOrder);
    }

    @Test
    public void testCheckDateExists() {
        LocalDate today = LocalDate.now();
        Assertions.assertFalse(dao.checkDateExists(today));
        Assertions.assertTrue(dao.checkDateExists(TOMORROW));
    }

    @Test
    public void testCheckOrderExists() {
        Order firstOrderTomorrow = new Order(TOMORROW, "John Smith", "WA", "Carpet", new BigDecimal("123"));
        dao.addOrder(firstOrderTomorrow,TOMORROW);

        Order secondOrderTomorrow = new Order(TOMORROW, "Bob Jones","CA", "Wood", new BigDecimal("321"));
        dao.addOrder(secondOrderTomorrow,TOMORROW);

        Order orderWeekFromNow = new Order(A_WEEK_FROM_NOW, "James Bond", "Georgia", "Marble", new BigDecimal("999"));
        dao.addOrder(orderWeekFromNow,A_WEEK_FROM_NOW);


        // Check that the first order that exist has order number of 1
        // If there is an order number with number 0, something is seriously wrong
        Assertions.assertFalse(dao.checkOrderExists(TOMORROW, 0));

        Assertions.assertTrue(dao.checkOrderExists(TOMORROW, 1));
        Assertions.assertTrue(dao.checkOrderExists(TOMORROW, 2));

        // Ensure that order number 3 exists a week from now but not tomorrow
        Assertions.assertFalse(dao.checkOrderExists(TOMORROW, 3));

        Assertions.assertTrue(dao.checkOrderExists(A_WEEK_FROM_NOW, 3));

    }

}
