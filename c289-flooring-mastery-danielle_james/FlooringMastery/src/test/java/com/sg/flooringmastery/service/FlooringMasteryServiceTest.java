package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductDao;
import com.sg.flooringmastery.dao.FlooringMasteryStateDao;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryServiceTest {
    FlooringMasteryService service;
    FlooringMasteryStateDao stateDaoStub;
    FlooringMasteryProductDao productDaoStub;
    FlooringMasteryOrderDao orderDaoStub;
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate DAYAFTERTOMORROW = LocalDate.now().plusDays(2);
    private static final LocalDate A_WEEK_FROM_NOW = LocalDate.now().plusWeeks(1);
    private List<String> stateList;
    private List<String> productList;
    private Order firstOrderTmr, secOrderTmr, thirdOrderNxt;

    @BeforeEach
    void setUp() throws FlooringMasteryPersistenceException, InvalidNameException, InvalidDateException {
        stateDaoStub = new FlooringMasteryStateDaoStubImpl();
        orderDaoStub = new FlooringMasteryOrderDaoStubImpl();
        productDaoStub = new FlooringMasteryProductDaoStubImpl();
        stateList = stateDaoStub.getAllStateAbbrv();
        productList = productDaoStub.getAllProductTypes();
        service = new FlooringMasteryServiceImpl(orderDaoStub, productDaoStub, stateDaoStub);

        firstOrderTmr = new Order(TOMORROW, "John Smith",
                stateList.get(0), productList.get(0), new BigDecimal("123"));
        secOrderTmr = new Order(TOMORROW, "Bob Jones",
                stateList.get(1), productList.get(1), new BigDecimal("321"));
        thirdOrderNxt = new Order(A_WEEK_FROM_NOW, "James Bond",
                stateList.get(2), productList.get(2), new BigDecimal("999"));
        service.createOrder(firstOrderTmr, TOMORROW);
        service.createOrder(secOrderTmr, TOMORROW);
        service.createOrder(thirdOrderNxt, A_WEEK_FROM_NOW);
    }

    @Test
    void createOrder() throws InvalidDateException, InvalidNameException{
        String productType = productList.get(2);
        String state=stateList.get(3);
        Order newOrder = new Order(A_WEEK_FROM_NOW ,"Achwaria Dion",
                state,productType,new BigDecimal(200));

        service.createOrder(newOrder, A_WEEK_FROM_NOW);
        assertTrue(service.getOrdersByDate(A_WEEK_FROM_NOW).contains(newOrder));

        Order failOrder = new Order(A_WEEK_FROM_NOW, "randomName!@",
                state, productType, new BigDecimal(200));
        assertThrows(InvalidNameException.class, () ->
                service.createOrder(failOrder, failOrder.getDate()));
    }

    @Test
    void removeOrder() throws InvalidDateException,
            InvalidNameException, OrderNotFoundException{
        String productType = productList.get(2);
        String state=stateList.get(2);
        Order orderToRemove = new Order(DAYAFTERTOMORROW,"Achdwaria Dion",
                state,productType,new BigDecimal(4.00));

        service.createOrder(orderToRemove, DAYAFTERTOMORROW);
        assertTrue(service.getOrdersByDate(DAYAFTERTOMORROW).contains(orderToRemove));
        assertEquals(orderToRemove,
                service.removeOrder(orderToRemove.getOrderNumber(), DAYAFTERTOMORROW));
        assertThrows(InvalidDateException.class,
                () -> service.getOrdersByDate(DAYAFTERTOMORROW));

        assertTrue(service.getOrdersByDate(TOMORROW).contains(secOrderTmr));
        assertEquals(secOrderTmr,
                service.removeOrder(secOrderTmr.getOrderNumber(), TOMORROW));
        assertEquals(service.getOrdersByDate(TOMORROW).size(), 1,
                "should only have 1 order left for tomorrow.");
    }
    @Test
    void editOrder() throws InvalidNameException, InvalidDateException {
        service.createOrder(firstOrderTmr, firstOrderTmr.getDate());
        firstOrderTmr.setState(stateList.get(3));
        firstOrderTmr.setProductType(productList.get(2));
        try {
            service.validateOrder(firstOrderTmr);
        } catch (FlooringMasteryPersistenceException | ValidationFloorAreaException e) {
            fail("Validation of order with correct data should not raise exception");
        }
        try {
            service.editOrder(TOMORROW, firstOrderTmr);
            Order editedOrder = service.retrieveOrder(TOMORROW, firstOrderTmr.getOrderNumber());
            assertEquals(firstOrderTmr, editedOrder);
        } catch (OrderNotFoundException e) {
            fail("Order editing failed.");
        }

        service.createOrder(thirdOrderNxt, thirdOrderNxt.getDate());
        thirdOrderNxt.setDate(DAYAFTERTOMORROW);
        try {
            service.validateOrder(thirdOrderNxt);
        } catch (FlooringMasteryPersistenceException | ValidationFloorAreaException e) {
            fail("validate order should not fail");
        }
        try {
            service.editOrder(A_WEEK_FROM_NOW, thirdOrderNxt);
            assertThrows(InvalidDateException.class,
                    () -> service.getOrdersByDate(A_WEEK_FROM_NOW));
            assertEquals(service.getOrdersByDate(DAYAFTERTOMORROW).size(), 1);
            assertTrue(service.getOrdersByDate(DAYAFTERTOMORROW).contains(thirdOrderNxt));
        } catch (OrderNotFoundException e) {
            fail("Order editing failed.");
        }
    }

    @Test
    void validateCorrectOrder() throws FlooringMasteryPersistenceException {
        LocalDate date = LocalDate.now().plusDays(2);
        String productType = productList.get(0);
        String state=stateList.get(0);
        BigDecimal area = new BigDecimal("231");
        Order correctOrder = new Order(date, "Valid Name",
                state, productType, area);
        try {
            service.validateOrder(correctOrder);
        } catch (FlooringMasteryPersistenceException | InvalidDateException
                 | ValidationFloorAreaException | InvalidNameException e) {
            fail("Correct order should not throw any error");
        }
        BigDecimal taxRate = stateDaoStub.getTaxRate(state);
        assertEquals(taxRate, correctOrder.getTaxRate(),
                "Tax rate does not match!");
        BigDecimal unitMatrlCost = productDaoStub.getUnitMatrlCost(productType);
        assertEquals(unitMatrlCost, correctOrder.getCost_per_sq_foot(),
                "unit material cost does not match ");
        BigDecimal unitLaborCost = productDaoStub.getUnitLaborCost(productType);
        assertEquals(unitLaborCost, correctOrder.getLabor_cost_per_sq_foot(),
                "unit labor cost does not match ");
        BigDecimal materialCost = unitMatrlCost.multiply(area);
        assertEquals(materialCost, correctOrder.getMaterialCost());

        BigDecimal laborCost = unitLaborCost.multiply(area);
        assertEquals(correctOrder.getLaborCost(), laborCost);
        BigDecimal tax = materialCost.add(laborCost).multiply(taxRate).divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(tax, correctOrder.getTax());
        BigDecimal total = materialCost.add(laborCost).add(tax)
                .setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(total, correctOrder.getTotal());
    }

    @Test
    void validateOrderException() {
        LocalDate wrongDate = LocalDate.now().plusDays(-2);
        LocalDate date = LocalDate.now().plusDays(2);
        BigDecimal area = new BigDecimal("231");
        BigDecimal wrongArea = new BigDecimal("99");
        String productType = productList.get(0);
        String state=stateList.get(0);

        Order wrongDateOrder = new Order(wrongDate, "Valid Name",
                state, productType, area);
        assertThrows(InvalidDateException.class, ()-> service.validateOrder(wrongDateOrder));

        Order wrongNameOrder = new Order(date, "invalid name!@#",
                state, productType, area);
        assertThrows(InvalidNameException.class, () -> service.validateOrder(wrongNameOrder));

        Order wrongAreaOrder = new Order(date, "Valid Name",
                state, productType, wrongArea);
        assertThrows(ValidationFloorAreaException.class, () -> service.validateOrder(wrongAreaOrder));
    }

    @Test
    void loadStates() throws FlooringMasteryPersistenceException {
        /*
        List<State> listState = new ArrayList<>();
        listState.add(new State("WA","Washington",new BigDecimal(3)));
        listState.add(new State("KY","Kentucky",new BigDecimal(6.00)));
        listState.add(new State( "CA","Calfornia",new BigDecimal(25.000)));
         */
        List<State> listState = service.loadStates();
        assertTrue(listState.size()==4);
    }

    @Test
    void testGetStateAbbrev() throws FlooringMasteryPersistenceException {
        List<String> listStateAbbrv = service.getStateAbbrev();
        assertTrue(listStateAbbrv.size() == 4, "4 states should be loaded from StateDaoStub");
        assertTrue(listStateAbbrv.contains("WA"));
        assertTrue(listStateAbbrv.contains("TX"));
        assertTrue(listStateAbbrv.contains("KY"));
        assertTrue(listStateAbbrv.contains("CA"));
    }

    @Test
    void loadProducts() throws FlooringMasteryPersistenceException {
        List<Product> listProduct = service.loadProducts();
                /*= new ArrayList<>();
        listProduct.add(new Product("Carpet",new BigDecimal(2.25),new BigDecimal(2.10)));
        listProduct.add(new Product("Laminate",new BigDecimal(1.75),new BigDecimal(2.10)));
        listProduct.add(new Product( "Tile",new BigDecimal(3.50),new BigDecimal(4.15)));
        listProduct.add(new Product( "Wood",new BigDecimal(5.15),new BigDecimal(4.75)));
                 */

        assertTrue(listProduct.size()==3);
    }

    @Test
    void calculateCostsTotal() {
        firstOrderTmr.setArea(new BigDecimal(2));
        firstOrderTmr.setLabor_cost_per_sq_foot(new BigDecimal(2));
        firstOrderTmr.setCost_per_sq_foot(new BigDecimal(2));
        firstOrderTmr.setTaxRate(new BigDecimal(25));

        assertEquals(new BigDecimal(10.00).setScale(2, RoundingMode.HALF_EVEN),
                service.calculateCostsTotal(firstOrderTmr));
    }
    @Test
    void validateOrderDate() {
        LocalDate yesterday = LocalDate.now().minusDays(2);
        assertThrows(InvalidDateException.class, () ->
                service.validateOrderDate(yesterday),"The date is past should not pass!");
    }


    @Test
    void validateCustomerName() {
    String name="";
    String name2="Valid name";
    assertThrows(InvalidNameException.class, () ->
                service.validateCustomerName(name),"InvalidName Exception is thrown");

    assertDoesNotThrow(() -> service.validateCustomerName(name2),"The name is valid, No Exception is thrown");
    }



    @Test
    void validateFloorArea() {
        BigDecimal InvalidArea=new BigDecimal(1);
        BigDecimal validArea=new BigDecimal(100);
        assertThrows(ValidationFloorAreaException.class, () ->
                service.validateFloorArea(InvalidArea),"InvalidArea Exception is thrown");

        assertDoesNotThrow(() -> service.validateFloorArea(validArea),"The area is valid, No Exception is thrown");

    }
}