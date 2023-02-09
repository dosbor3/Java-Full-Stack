package com.sg.vendingmachine.service;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;

import java.util.*;
import com.sg.vendingmachine.dto.*;

public interface VendingMachineServiceLayer {
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    Item getItem(String itemNo) throws VendingMachinePersistenceException;
    Item removeItem(String itemNo) throws VendingMachinePersistenceException,
            NoItemInventoryException, InsufficientFundsException;
    void getChange(Item item, Buyer buyer) throws NoItemInventoryException, InsufficientFundsException;
}
