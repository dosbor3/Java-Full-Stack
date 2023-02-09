package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.math.BigDecimal;




class FlooringMasteryProductDaoFileImplTest {
    public FlooringMasteryProductDao testDao;

    @BeforeEach
    void setUp() throws IOException {
        String testFile = "src/test/TestData/testProduct.txt";
        testDao = new FlooringMasteryProductDaoFileImpl();
        testDao.setProductFile(testFile);
    }

    @Test
    void getProducts() throws FlooringMasteryPersistenceException {
        assertNotNull(testDao.getProducts(), "The list of product types must not be null");
        assertEquals(4, testDao.getProducts().size(),"List of product types should have 4 products.");

        assertInstanceOf(Product.class, testDao.getProducts().get(0));
        assertInstanceOf(Product.class, testDao.getProducts().get(1));
        assertInstanceOf(Product.class, testDao.getProducts().get(2));
        assertInstanceOf(Product.class, testDao.getProducts().get(3));
    }

    @Test
    void getAllProductTypes() throws FlooringMasteryPersistenceException {

        assertNotNull(testDao.getProducts(), "The list of product types must not be null");
        assertEquals(4, testDao.getAllProductTypes().size(),"List of product types should have 4 products.");

        assertTrue(testDao.getAllProductTypes().contains("Carpet"),
                "The list of products should include Carpet.");
        assertTrue(testDao.getAllProductTypes().contains("Laminate"),
                "The list of products should include Laminate.");
        assertTrue(testDao.getAllProductTypes().contains("Tile"),
                "The list of product types should include Tile.");
        assertTrue(testDao.getAllProductTypes().contains("Wood"),
                "The list of product types should include Wood.");
    }

    @Test
    void getUnitMatrlCost() throws FlooringMasteryPersistenceException {
        assertEquals(new BigDecimal("2.25"), testDao.getUnitMatrlCost("Carpet"));
        assertEquals(new BigDecimal("1.75"), testDao.getUnitMatrlCost("Laminate"));
        assertEquals(new BigDecimal("3.50"), testDao.getUnitMatrlCost("Tile"));
        assertEquals(new BigDecimal("5.15"), testDao.getUnitMatrlCost("Wood"));
    }

    @Test
    void getUnitLaborCost() throws FlooringMasteryPersistenceException {
        assertEquals(new BigDecimal("2.10"), testDao.getUnitLaborCost("Carpet"));
        assertEquals(new BigDecimal("2.10"), testDao.getUnitLaborCost("Laminate"));
        assertEquals(new BigDecimal("4.15"), testDao.getUnitLaborCost("Tile"));
        assertEquals(new BigDecimal("4.75"), testDao.getUnitLaborCost("Wood"));
    }

}