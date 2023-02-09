package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.*;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String itemNo) throws VendingMachinePersistenceException {
        return dao.getItem(itemNo);
    }

    @Override
    public Item removeItem(String itemNo) throws VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        Item removeditem = dao.removeItem(itemNo);
        auditDao.writeAuditEntry("Item " + itemNo + "REMOVED.");
        return removeditem;
    }

    @Override
    public BigDecimal getChange(Item item, Buyer buyer) throws NoItemInventoryException, InsufficientFundsException {
        return null;
    }


}
