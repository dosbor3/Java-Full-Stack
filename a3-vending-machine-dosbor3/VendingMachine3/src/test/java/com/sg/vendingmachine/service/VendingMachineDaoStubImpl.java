package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {
    public Item onlyItem;


    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("SI08");
        onlyItem.setItemName("Oreo Cookies");
        onlyItem.setItemCost("1.50");
        onlyItem.setItemQuantity("5");
    }



    public VendingMachineDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    /**
     * @return
     */
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    /**
     * @param itemNo
     * @return
     */
    @Override
    public Item getItem(String itemNo) {
        if(itemNo.equals(onlyItem.getItemNo())) {
            return onlyItem;
        }
        else {
            return null;
        }
    }

    /**
     * @param itemNo
     * @return
     */
    @Override
    public Item removeItem(String itemNo) throws VendingMachinePersistenceException {
        if(itemNo.equals(onlyItem.getItemNo())) {
            return onlyItem;
        }
        else {
            return null;
        }
    }
}
