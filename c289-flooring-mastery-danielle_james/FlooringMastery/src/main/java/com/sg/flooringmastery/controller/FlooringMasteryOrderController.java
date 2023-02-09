package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.service.*;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryOrderController {
    private FlooringMasteryService service;
    private FlooringMasteryView view;

    @Autowired
    public FlooringMasteryOrderController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }
    public void run(){
        boolean keepGoing = true;
        try {
            service.loadAllOrders();
        } catch (FlooringMasteryPersistenceException e) {
            view.printErrMsg(e.getMessage());
        }
        while (keepGoing) {
            int userChoice = view.showMainMenuAndGetResponse();
            switch (userChoice) {
                case 1: {
                    this.displayOrders();
                    break;
                }
                case 2: {
                    this.addOrder();
                    break;
                }
                case 3: {
                    this.editOrder();
                    break;
                }
                case 4: {
                    this.removeOrder();
                    break;
                }
                case 5: {
                    this.exportData();
                    break;
                }
                case 6: {
                    keepGoing = false;
                    quitProgram();
                    break;
                }
            }
        }
        try {
            service.writeAllOrders();
        } catch (FlooringMasteryPersistenceException e) {
            view.printErrMsg(e.getMessage());
        }
    }

    private void displayOrders(){
        try {
            LocalDate dateOfOrder = view.promptUserOrderDate();
            List<Order> orders = service.getOrdersByDate(dateOfOrder);
            view.displayOrdersOnDay(orders);
        } catch (InvalidDateException e) {
            view.printErrMsg(e.getMessage()); //view.printErrMessage(e.getMessage());
        }
    }

    private void addOrder(){
        try {
            List<String> stateNames = service.getStateAbbrev();
            List<Product> products = service.loadProducts();
            Order order = view.addOrder(stateNames, products);
            service.validateOrder(order);
            if (view.userIsSureOfOrderCreation(order)) {
                service.createOrder(order, order.getDate());
            }
        } catch (FlooringMasteryPersistenceException | InvalidDateException | ValidationFloorAreaException |
                 InvalidNameException e) {
            view.printErrMsg(e.getMessage());
        }

    }

    private void editOrder() {
        try {
            LocalDate dateOfOrder = view.promptUserForDate();
            int orderNumber = view.promptUserForOrderNumber();
            Order order = service.retrieveOrder(dateOfOrder, orderNumber);

            int response;
            boolean keepAsk = true;
            do {
                response = view.showEditOrderMenuAndGetResponse();
                switch (response) {
                    case 1: {
                        view.editName(order);
                        break;
                    }
                    case 2: {
                        view.editState(order);
                        break;
                    }
                    case 3: {
                        view.editProductType(order);
                        break;
                    }
                    case 4: {
                        view.editArea(order);
                        break;
                    }
                    case 5:
                        keepAsk = false;
                        break;
                    default: {
                        view.displayInvalidSelection();
                    }
                }
            } while (keepAsk);
            service.validateOrder(order); // make sure new information is valid
            if (view.userIsSureOfOrderCreation(order)) {
                service.editOrder(dateOfOrder, order);
            }
        } catch (InvalidDateException | OrderNotFoundException |
                 InvalidNameException | ValidationFloorAreaException | FlooringMasteryPersistenceException e) {
            view.printErrMsg(e.getMessage());
        }
    }

    private void removeOrder() {
        int orderNumber = view.promptUserForOrderNumber();
        LocalDate dateOfOrder = view.promptUserForDate();

        try {
            service.removeOrder(orderNumber, dateOfOrder);
        } catch (OrderNotFoundException | InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportData() {
        try {
            service.exportOrders();
        } catch (FlooringMasteryPersistenceException e) {
            view.printErrMsg(e.getMessage());
        }
    }

    private void quitProgram() {
        view.quitMessaage();
    }

}
