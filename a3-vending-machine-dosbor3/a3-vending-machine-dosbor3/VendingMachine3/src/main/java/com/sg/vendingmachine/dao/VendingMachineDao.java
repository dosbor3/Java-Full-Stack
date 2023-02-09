package com.sg.vendingmachine.dao;
import  com.sg.vendingmachine.dto.*;

import java.math.BigDecimal;
import java.util.*;

public interface VendingMachineDao {
    /**
     *
     * @return
     */
    List<Item> getAllItems() throws VendingMachinePersistenceException;

    /**
     *
     * @param itemNo
     * @return
     */
    Item getItem(String itemNo);

    /**
     *
     * @param itemNo
     * @return
     */
    Item removeItem(String itemNo);
}
