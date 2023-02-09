package com.sg.flooringmastery.ui;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


@Component("view")
public class FlooringMasteryView {
    @Autowired
    private UserIO io;


    // There might be a cleaner way to do this; the last line of this method looks awkward
    public int showMainMenuAndGetResponse() {
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.println("* <<Flooring Program>>");
        io.println("* 1. Display Orders");
        io.println("* 2. Add an Order");
        io.println("* 3. Edit an Order");
        io.println("* 4. Remove an Order");
        io.println("* 5. Export All Data");
        io.println("* 6. Quit");
        io.println("*");
        return io.readInt("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *",
                1, 6);
    }

    public LocalDate promptUserOrderDate() {
        return io.readLocalDate("What's the order date? (MM-dd-yyyy)");
    }

    // TODO: Test this method and make sure it does what it's supposed to do
    public void displayOrdersOnDay(List<Order> orders) {
        orders.stream()
                .forEach(order -> printOrder(order));
    }

    private String listAndGetStates(List<String> states) {
        int stateIndexChosen = -1;
        io.println("Which state is this order taking place in?");
        for (int i = 0; i < states.size(); i++) {
            io.println((i+1) + ": " + states.get(i));
        }
            stateIndexChosen = io.readInt("Select a state, enter index for state: ",
                    1, states.size());

        return states.get(stateIndexChosen - 1);
    }

    private String listAndGetProducts(List<Product> products) {
        int productIndexChosen = -1;
        io.println("Which product is being used for the flooring order?");
        for (int i = 0; i < products.size(); i++) {
            String productName = products.get(i).getProductType();
            BigDecimal productCostPerSqFt = products.get(i).getCost_per_sq_foot();
            BigDecimal laborCostPerSqFt = products.get(i).getLabor_cost_per_sq_foot();
            io.println(((i+1) + ": " + productName + " | unit material cost: "
                    + productCostPerSqFt + " | unit labor cost: " + laborCostPerSqFt));
            io.print("------------------------------------------");
        }
        productIndexChosen = io.readInt("Select a product, enter the index below: ",
                1, products.size());

        return products.get(productIndexChosen - 1).getProductType();
    }

    // TODO: Check that these prompts make sense (I'm not a master on flooring)
    public Order addOrder(List<String> states, List<Product> products) {
        LocalDate orderDate = io.readLocalDate("What's the date of the order (MM-dd-yyyy)");
        String name = io.readString("What's your name?");
        String state = listAndGetStates(states);
        String productType = listAndGetProducts(products);
        BigDecimal area = io.readBigDecimal("What's the area in square feet of the flooring being ordered?");

        return new Order(orderDate, name, state, productType, area);
    }

    public LocalDate promptUserForDate() {
        return io.readLocalDate("What's the date? mm-dd-yyyy");
    }

    public void quitMessaage() {
        io.println("Thank you for your business!");
    }

    public int promptUserForOrderNumber() {
        return io.readInt("What's the order number?");
    }

    public void displayInvalidSelection() {
        io.println("Invalid selection! Pick again!");
    }

    public void printOrder(Order order) {
        io.println("Order number: " + order.getOrderNumber());
        io.println("Customer name: " + order.getCustomerName());
        io.println("State: " + order.getState());
        io.println("Tax rate: " + order.getTaxRate());
        io.println("Type of flooring: " + order.getProductType());
        io.println("Area: " + order.getArea());
        io.println("-------------------------------------------------");
        io.println("Unit material cost: " + order.getCost_per_sq_foot());
        io.println("Unit labor cost: " + order.getLabor_cost_per_sq_foot());
        io.println("Material cost : " + order.getMaterialCost());
        io.println("Labor cost: " + order.getLaborCost());
        io.println("Tax: " + order.getTax());
        io.println("Total: " + order.getTotal());
        io.println("-------------------------------------------------");
    }
    public boolean userIsSureOfOrderCreation(Order order) {
        io.println("-------------------------------------------------");
        io.println("You've created an order: ");
        this.printOrder(order);
        return io.readBoolean("Are you sure you want to create this order? (Y/N)");
    }

    public String promptEditOrderAndGetName() {
       return io.readString("Who's order would you like to edit?");
    }

    public int showEditOrderMenuAndGetResponse() {
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.println("* 1. Edit name");
        io.println("* 2. Edit state");
        io.println("* 3. Edit product type");
        io.println("* 4. Edit area");
        io.println("* 5. Exit");
        io.println("*");
        return io.readInt("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *",
                1, 5);
    }

    public Order editName(Order order) {
        io.println("The current customer name is " + order.getCustomerName());
        io.print("------------------------------------------");
        String newName = io.readString("What's the new name for the order?");
        if (! newName.isBlank())
            order.setCustomerName(newName);
        return order;
    }

    public Order editState(Order order) {
        io.println("The current state is " + order.getCustomerName());
        io.print("------------------------------------------");
        String newState = io.readString("What's the new state for the order?");
        if (newState.compareTo("") != 0)
            order.setCustomerName(newState);
        return order;
    }
    public Order editProductType(Order order) {
        io.println("The current product type is " + order.getCustomerName());
        io.print("------------------------------------------");
        String newProductType = io.readString("What's the new product type for the order?");
        if (newProductType.compareTo("") != 0)
            order.setProductType(newProductType);
        return order;
    }

    public Order editArea(Order order) {
        io.println("The current area is " + order.getCustomerName());
        io.print("------------------------------------------");
        BigDecimal newArea = io.readBigDecimal("What's the new area for the order?");
        if (newArea.compareTo(new BigDecimal("0")) != 0)
            order.setArea(newArea);
        return order;
    }

    public LocalDate promptRemoveOrderGetDate() {
        return io.readLocalDate("What's the date of the order you would like to remove?");
    }

    public int promptRemoveOrderGetOrderNumber() {
        return io.readInt("What's the order number of the order you would like to remove?");
    }
    public void printErrMsg(String message) {
        io.println(message);
    }
}
