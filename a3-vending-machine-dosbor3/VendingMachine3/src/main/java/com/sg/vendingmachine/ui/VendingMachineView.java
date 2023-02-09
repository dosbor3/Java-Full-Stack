package com.sg.vendingmachine.ui;
import com.sg.vendingmachine.dto.*;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class VendingMachineView {
    private UserIO io;

    @Autowired
    public VendingMachineView( UserIO io) {
        this.io = io;
    }

    public void displayVendItems(List<Item> itemList) {
        io.print("~//~//~ Vend Items Available ~//~//~");
        for(Item currentItem: itemList){
            String itemInfo = String.format("\t\t%s--> %s \t$%5s",
                    currentItem.getItemNo(),
                    currentItem.getItemName(),
                    currentItem.getItemCost());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    public String getFunds() throws InsufficientFundsException {
        String funds = io.readString("Insert Funds: ");
        if(funds.isEmpty() || funds.isEmpty() ) {
            throw new InsufficientFundsException("You MUST insert Cash BEFORE purchase...");
        }
        else {
            return funds;
        }
    }
//
    public int printAndGetActionSelection() {
        io.print("Main Menu Selection");
        io.print("1. View Item Info");
        io.print("2. Purchase Item");
        io.print("3. View All Items");
        io.print("4. Exit");
        return io.readInt("Please select an action from the main menu above: ",1,4);
    }

    public String getItemSelection() {
        return io.readString("Enter itemNo for selection: ").toUpperCase();
    }

    public void displayItem(Item item) {
        if (item != null) {
            io.print("Item Selected: " + item.getItemName() + "\t$" + item.getItemCost());
            io.print("");
        }
        else {
            io.print("No such Item");
        }
        io.readString("Please hit enter to continue.");
    }
//TODO Make changes to DaoFileImple..
    public void displayPurchasedItem(Item itemPurchased, Buyer buyer) throws NoItemInventoryException, InsufficientFundsException {
        if(Integer.parseInt(itemPurchased.getItemQuantity()) < 1 ) {
            throw new NoItemInventoryException("Item Sold Out");
        }

        if(Double.parseDouble(buyer.getFunds()) < Double.parseDouble(itemPurchased.getItemCost())) {
            throw new InsufficientFundsException("Not Enough Funds to Purchase this Item...");
        }


        if(itemPurchased.getItemNo() != null) {
            double cost = Double.parseDouble(itemPurchased.getItemCost());
            double funds = Double.parseDouble(buyer.getFunds());
            io.print(itemPurchased.getItemName() + " vended");
        }
        else {
            io.print("No such item....");
        }
        io.readString("Please hit enter to have change refunded.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
}
