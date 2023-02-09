package com.sg.vendingmachine.controller;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.*;
import com.sg.vendingmachine.dto.*;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VendingMachineController {
    private VendingMachineView view ;
    private VendingMachineServiceLayer service ;

    @Autowired
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        boolean keepGoing = true;
        int menuSelection = 0;
        printItemList();

        while (keepGoing) {

            menuSelection = getActionSelection();

            switch (menuSelection) {
                case 1:
                    viewItem();
                    break;
                case 2:
                    purchaseItem();
                    break;
                case 3:
                    printItemList();
                    break;
                case 4:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    }

    private void printItemList() throws VendingMachinePersistenceException {
        List<Item> itemList = service.getAllItems();
        view.displayVendItems(itemList);
    }

    private int getActionSelection() {
        return view.printAndGetActionSelection();
    }

    private void viewItem() throws VendingMachinePersistenceException {
        String itemNo = view.getItemSelection();
        Item item = service.getItem(itemNo);
        view.displayItem(item);
    }
    private void purchaseItem() throws VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        String funds = view.getFunds();
        Buyer buyer = new Buyer(funds);
        String itemNo = view.getItemSelection();
        Item item = service.getItem(itemNo);
        service.removeItem(itemNo);
        view.displayPurchasedItem(item, buyer);
        service.getChange(item, buyer);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
