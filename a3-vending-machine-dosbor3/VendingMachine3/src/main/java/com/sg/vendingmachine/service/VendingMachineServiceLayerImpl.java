package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    @Autowired
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
    public Item removeItem(String itemNo) throws VendingMachinePersistenceException, NoItemInventoryException {
        if(dao.getItem(itemNo).getItemQuantity().equals("0")) {
            auditDao.writeAuditEntry(dao.getItem(itemNo).getItemName() + " OUT OF STOCK");
            throw new NoItemInventoryException(dao.getItem(itemNo).getItemName() + " is OUT OF STOCK");
        }
        int quantity = Integer.parseInt(dao.getItem(itemNo).getItemQuantity());
        quantity -= 1;

        dao.getItem(itemNo).setItemQuantity(String.valueOf(quantity));

        Item removedItem = dao.getItem(itemNo);
        auditDao.writeAuditEntry(itemNo + dao.getItem(itemNo).getItemName() + " REMOVED.");
        return removedItem;
    }

    @Override
    public void getChange(Item item, Buyer buyer) throws NoItemInventoryException, InsufficientFundsException {
        if (buyer.getFunds() == null
                || buyer.getFunds().isBlank()
                || buyer.getFunds().isEmpty()
                || Double.parseDouble(buyer.getFunds()) < Double.parseDouble(item.getItemCost()) ) {
            throw new InsufficientFundsException("Not Enough Funds available");
        }
        String funds = buyer.getFunds();
        String cost  = item.getItemCost();
        Change.calculateChange(cost, funds);

    }


}
