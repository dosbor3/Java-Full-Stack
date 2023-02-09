package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.State;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.math.BigDecimal;

class FlooringMasteryStateDaoFileImplTest {
    public FlooringMasteryStateDao testDao;

    @BeforeEach
    void setUp() throws IOException{
        String testFile = "src/test/TestData/testTaxes.txt";
        testDao = new FlooringMasteryStateDaoFileImpl();
        testDao.setFilePath(testFile);
    }

    @Test
    void getAllStates() throws FlooringMasteryPersistenceException {
        assertNotNull(testDao.getAllStates(), "The list of State objects must not be null");
        assertEquals(4, testDao.getAllStates().size(),"List of State objects should have 4 products.");

        assertInstanceOf(State.class, testDao.getAllStates().get(0));
        assertInstanceOf(State.class, testDao.getAllStates().get(1));
        assertInstanceOf(State.class, testDao.getAllStates().get(2));
        assertInstanceOf(State.class, testDao.getAllStates().get(3));
    }

    @Test
    void getAllStateAbbrv() throws FlooringMasteryPersistenceException {
        assertNotNull(testDao.getAllStateAbbrv(), "The list of State objects must not be null");
        assertEquals(4, testDao.getAllStateAbbrv().size(),"List of State should have 4 State objects.");

        assertTrue(testDao.getAllStateAbbrv().contains("TX"),
                "The list of States should include TX.");
        assertTrue(testDao.getAllStateAbbrv().contains("WA"),
                "The list of States should include WA.");
        assertTrue(testDao.getAllStateAbbrv().contains("KY"),
                "The list of States should include KY.");
        assertTrue(testDao.getAllStateAbbrv().contains("CA"),
                "The list of States should include CA.");
    }

    @Test
    void getTaxRate() throws FlooringMasteryPersistenceException {
        assertEquals(new BigDecimal("4.45"), testDao.getTaxRate("TX"));
        assertEquals(new BigDecimal("9.25"), testDao.getTaxRate("WA"));
        assertEquals(new BigDecimal("6.00"), testDao.getTaxRate("KY"));
        assertEquals(new BigDecimal("25.01"), testDao.getTaxRate("CA"));
    }
}