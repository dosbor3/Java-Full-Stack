package com.sg.vendingmachine.controller;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.*;
import com.sg.vendingmachine.dto.*;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.*;

import java.math.BigDecimal;
import java.util.*;

public class VendingMachineController {
    private VendingMachineView view ;
    private VendingMachineServiceLayer service ;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws VendingMachinePersistenceException {
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

    private void printItemList() throws VendingMachinePersistenceException, VendingMachinePersistenceException {
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
    private void purchaseItem() throws VendingMachinePersistenceException {
        String funds = view.getFunds();
        Buyer buyer = new Buyer(funds);
        String itemNo = view.getItemSelection();
        Item item = service.getItem(itemNo);
        view.displayPurchasedItem(item, buyer);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
