package com.sg.vendingmachine.dto;

public class Item {
    private String itemNo;
    private String itemName;
    private String itemCost;
    private String itemQuantity;

    public Item(String itemNo) {
        this.itemNo = itemNo;
    }

    public void setItemNo(String ItemNo) {this.itemNo = itemNo;}
    public String getItemNo() {return itemNo;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public String getItemName() {return this.itemName;}
    public void setItemCost(String cost) {this.itemCost = cost;}
    public String getItemCost() {return this.itemCost;}
    public void setItemQuantity(String quantity){this.itemQuantity = quantity;}
    public String getItemQuantity() {return this.itemQuantity;}
}
