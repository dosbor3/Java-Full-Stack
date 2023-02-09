package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements  VendingMachineAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //this method does nothing, this method allows the Service Layer to make a call to
        //the Audit DAO but nothing will get written to the Audit Log File
    }
}
