package com.sg.vendingmachine.ui;
import com.sg.vendingmachine.dto.*;
import java.util.*;
public class VendingMachineView {
    private UserIO io;

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
        io.print("\n\n");
        io.readString("Please hit enter to continue.");
    }
    public String getFunds() {
        return io.readString("Insert Funds: ");
    }
//    public String addFunds(Buyer buyer) {
//        funds = buyer.get
//        do {
//            funds += Double.parseDouble(io.readString("Insert Funds: "));
//            String formatted = String.format("%2.2f", funds);
//            io.print("Total Amount Inserted(Must be greater than $.50): $" + formatted);
//            io.print("");
//        } while (funds < );
//        return String.valueOf(funds);
//    }

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
            io.print("\nItem Selected: " + item.getItemName() + "\t$" + item.getItemCost());
            io.print("");
        }
        else {
            io.print("No such Item");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayPurchasedItem(Item itemPurchased, Buyer buyer) {
        if(itemPurchased != null ) {
            if (Integer.parseInt(itemPurchased.getItemQuantity()) > 0) {
                double cost = Double.parseDouble(itemPurchased.getItemCost());
                double funds = Double.parseDouble(buyer.getFunds());
                if (cost > funds) {
                    io.print("You do not have enough funds to purchase this item");
                    io.print("Funds Available: " + funds);
                } else {
                    io.print(itemPurchased.getItemName() + " vended");
                }
            }
        }
        else {
            io.print("No such item....");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
}
