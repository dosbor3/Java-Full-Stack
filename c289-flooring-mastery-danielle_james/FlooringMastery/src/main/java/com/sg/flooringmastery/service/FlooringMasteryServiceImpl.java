package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.State;
import com.sg.flooringmastery.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component("service")
public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    @Autowired
    private FlooringMasteryOrderDao flooringMasteryOrderDao;
    @Autowired
    private FlooringMasteryProductDao flooringMasteryProductDao;
    @Autowired
    private FlooringMasteryStateDao flooringMasteryStateDao;

    public FlooringMasteryServiceImpl(FlooringMasteryOrderDao orderDao,
                                      FlooringMasteryProductDao productDao,
                                      FlooringMasteryStateDao stateDao) {
        this.flooringMasteryOrderDao = orderDao;
        this.flooringMasteryProductDao = productDao;
        this.flooringMasteryStateDao = stateDao;
    }

    /**
     *
     * @param order
     * @param orderDate
     * @throws InvalidDateException
     * @throws OrderAlreadyExistException
     * @throws InvalidNameException
     */
    @Override
    public void createOrder(Order order, LocalDate orderDate) throws
            InvalidDateException, InvalidNameException {
        if (validateCustomerName(order.getCustomerName())
                && validateOrderDate(orderDate)) {
            //order.setOrderNumber(order.hashCode());
            flooringMasteryOrderDao.addOrder(order,orderDate);
        }
    }

    /**
     *
     * @param orderNumber
     * @param orderDate
     * @return
     * @throws InvalidDateException
     * @throws OrderNotFoundException
     */
    @Override
    public Order removeOrder(int orderNumber, LocalDate orderDate) throws InvalidDateException, OrderNotFoundException {
        Order removedOrder;
        if (!flooringMasteryOrderDao.checkDateExists(orderDate)) {
            throw new InvalidDateException("::Error: Invalid Date.");
        } else if (!flooringMasteryOrderDao.checkOrderExists(orderDate, orderNumber)) {
            throw new OrderNotFoundException("Order Not Found");
        } else removedOrder = flooringMasteryOrderDao.removeOrder(orderDate, orderNumber);
        return removedOrder;
    }

    @Override
    public Order validateOrder(Order newOrder) throws FlooringMasteryPersistenceException,
            InvalidDateException, ValidationFloorAreaException, InvalidNameException {
        validateCustomerName(newOrder.getCustomerName());
        validateOrderDate(newOrder.getDate());
        validateFloorArea(newOrder.getArea());
        String state = newOrder.getState();
        String productType = newOrder.getProductType();
        newOrder.setTaxRate(flooringMasteryStateDao.getTaxRate(state));
        newOrder.setCost_per_sq_foot(
                flooringMasteryProductDao.getUnitMatrlCost(productType));
        //TODO remove restriction placed in the view for product type
        newOrder.setLabor_cost_per_sq_foot(
                flooringMasteryProductDao.getUnitLaborCost(productType));
        calculateCostsTotal(newOrder);
        return newOrder;
    }

    @Override
    public int loadAllOrders() throws FlooringMasteryPersistenceException {
        return flooringMasteryOrderDao.loadAllDayOrders();
    }

    @Override
    public int exportOrders() throws FlooringMasteryPersistenceException {
        return flooringMasteryOrderDao.exportOrders();
    }

    @Override
    public int writeAllOrders() throws FlooringMasteryPersistenceException {
        return flooringMasteryOrderDao.writeAllDayOrders();
    }

    /**
     * @param oldDate
     * @param newOrder
     * @throws InvalidDateException
     * @throws OrderNotFoundException
     */
    @Override
    public void editOrder(LocalDate oldDate, Order newOrder) throws InvalidDateException, OrderNotFoundException {
        if (!flooringMasteryOrderDao.checkDateExists(oldDate)){
            throw new InvalidDateException("::Error: Invalid Date.");
        }
        else if (!flooringMasteryOrderDao.checkOrderExists(oldDate, newOrder.getOrderNumber())){
            throw new OrderNotFoundException("Order Not Found");
        }
        else flooringMasteryOrderDao.editOrder(oldDate, newOrder);
    }

    @Override
    public Order retrieveOrder(LocalDate orderDate, int orderNo) throws OrderNotFoundException, InvalidDateException {
        if (!flooringMasteryOrderDao.checkDateExists(orderDate)) {
            throw new InvalidDateException("::Error: Invalid Date.");
        } else if (!flooringMasteryOrderDao.checkOrderExists(orderDate, orderNo)) {
            throw new OrderNotFoundException("Order not found");
        } return flooringMasteryOrderDao.retrieveOrder(orderDate, orderNo);
    }

    /**
     * This method return a list of orders by a specific date
     * @param orderDate
     * @return
     * @throws InvalidDateException
     */
    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) throws InvalidDateException {
        if ( !flooringMasteryOrderDao.checkDateExists(orderDate)) {
            throw new InvalidDateException("No Orders Found For That Date.");
        }
        List<Order> listOfOrders = flooringMasteryOrderDao.getOrderOnDay(orderDate);
        return listOfOrders;
    }

    /**
     * @return a list of states
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public List<State> loadStates() throws FlooringMasteryPersistenceException {
        return flooringMasteryStateDao.getAllStates();
    }

    @Override
    public List<String> getStateAbbrev() throws FlooringMasteryPersistenceException {
        return flooringMasteryStateDao.getAllStateAbbrv();
    }

    /**
     *
     * @return a list of product
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public List<Product> loadProducts() throws FlooringMasteryPersistenceException {
        return flooringMasteryProductDao.getProducts();
    }

    /**
     *
     * @return a list of product types
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public List<String> loadProductsType() throws FlooringMasteryPersistenceException {
        return flooringMasteryProductDao.getAllProductTypes();
    }

    /**
     *
     * @param order
     * @return
     * MaterialCost = (Area * CostPerSquareFoot)
     * LaborCost = (Area * LaborCostPerSquareFoot)
     * Tax = (MaterialCost + LaborCost) * (TaxRate/100)
     * Tax rates are stored as whole numbers
     * Total = (MaterialCost + LaborCost + Tax)
     */
    @Override
    public BigDecimal calculateCostsTotal(Order order) {
        BigDecimal materialCost= calculateMaterialCost(order);
        order.setMaterialCost(materialCost.setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal laborCost=calculateLaborCost(order);
        order.setLaborCost(laborCost.setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal tax= calculateTax( order);
        order.setTax(tax.setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal total;
        total = materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_EVEN);
        order.setTotal(total);
        return total;
    }

    @Override
    public BigDecimal calculateMaterialCost(Order order) {
        BigDecimal materialCost= order.getArea().multiply(order.getCost_per_sq_foot());
                //.setScale(2, RoundingMode.HALF_EVEN);
        return materialCost;
    }

    @Override
    public BigDecimal calculateLaborCost(Order order) {
        BigDecimal laborCost=order.getArea().multiply(order.getLabor_cost_per_sq_foot());
                //.setScale(2, RoundingMode.HALF_EVEN);
        return laborCost;
    }

    @Override
    public BigDecimal calculateTax(Order order) {
        BigDecimal materialCost= calculateMaterialCost(order);
        BigDecimal laborCost=calculateLaborCost(order);
        BigDecimal taxPercentage = order.getTaxRate().divide(new BigDecimal("100"));;
        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxPercentage);

        return tax;
    }

    @Override
    public Boolean validateOrderDate(LocalDate orderDate) throws InvalidDateException {
        LocalDate today = LocalDate.now();
        if (today.isBefore(orderDate)){
            return true;
        }
        throw new InvalidDateException(
                "ERROR :: Date must be in the future!"
        );
    }

    @Override
    public Boolean validateCustomerName(String costumerName) throws InvalidNameException {
        if (costumerName.isEmpty() || !costumerName.matches("[A-Za-z0-9,. ]+")) {
            throw new InvalidNameException("Invalid Name");}
        else return true;
    }

    @Override
    public Boolean validateFloorArea(BigDecimal area) throws ValidationFloorAreaException {
        if (area.compareTo(BigDecimal.valueOf(100)) >= 0){
            return true;
        }
        throw new ValidationFloorAreaException(
                "The area must be a positive decimal. Minimum order size is 100 sq ft"
        );
    }




}
