package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Buyer;
import com.sg.vendingmachine.dto.Item;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }

    @org.junit.jupiter.api.Test
    void getAllItems() throws VendingMachinePersistenceException {
        // ARRANGE
        Item testClone = new Item("SI08");
        testClone.setItemName("Oreo Cookies");
        testClone.setItemCost("1.50");
        testClone.setItemQuantity("5");

        // ACT & ASSERT
        assertEquals(1, service.getAllItems().size(),
                "Should only have one item.");
        assertTrue(service.getAllItems().contains(testClone),
                "The one item should be Oreo Cookies.");
    }

    @org.junit.jupiter.api.Test
    void getItem() throws VendingMachinePersistenceException {
        // ARRANGE
        Item testClone = new Item("SI08");
        testClone.setItemName("Oreo Cookies");
        testClone.setItemCost("1.50");
        testClone.setItemQuantity("5");

        // ACT & ASSERT
        Item shouldBeOreo = service.getItem("0001");
        assertNotNull(shouldBeOreo, "Getting SI08 should be not null.");
        assertEquals( testClone, shouldBeOreo, "Item stored under SI08 should be Oreo Cookies.");

        Item shouldBeNull = service.getItem("SI09");
        assertNull( shouldBeNull, "Getting SI09 should be null.");
    }

    @org.junit.jupiter.api.Test
    void removeItem() throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        // ARRANGE
        Item testClone = new Item("SI08");
        testClone.setItemName("Oreo Cookies");
        testClone.setItemCost("1.50");
        testClone.setItemQuantity("5");


        // ACT & ASSERT
        Item shouldBeAda = service.removeItem("SI08");
        assertNotNull( shouldBeAda, "Removing SI08 should be not null.");
        assertEquals( testClone, shouldBeAda, "Item removed from SI08 should be Oreo Cookies.");

        Item shouldBeNull = service.removeItem("SI09");
        assertNull( shouldBeNull, "Removing SI09 should be null.");
    }

    @org.junit.jupiter.api.Test
    void getChange() throws VendingMachinePersistenceException {
        // ARRANGE
        Item testClone = new Item("SI08");
        testClone.setItemName("Oreo Cookies");
        testClone.setItemCost("1.50");
        testClone.setItemQuantity("5");
        Buyer testBuyer = new Buyer("1.75");


        // ACT & ASSERT
        Item shouldBeOreo = service.getItem("SI08");
        assertNotNull(shouldBeOreo, "Getting SIO8 Should be Not Null");
        assertEquals( testClone, shouldBeOreo, "Item stored under SI08 should be Oreo Cookies.");

        String fundsShouldBe = testBuyer.getFunds();
        assertNotNull(fundsShouldBe, "Getting 1.50 funds should not be null");
        assertEquals( testBuyer.getFunds(), fundsShouldBe, "funds stored under testBuyer should be 1.50.");



    }
}