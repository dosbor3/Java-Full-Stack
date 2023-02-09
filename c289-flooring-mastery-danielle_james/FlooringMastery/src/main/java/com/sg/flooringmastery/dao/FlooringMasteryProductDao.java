package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public interface FlooringMasteryProductDao {
    List <Product> getProducts() throws FlooringMasteryPersistenceException;
    List<String> getAllProductTypes() throws FlooringMasteryPersistenceException;
    Product unmarshallProduct(String line);
    //Return second token from file
    BigDecimal getUnitMatrlCost(String productType) throws FlooringMasteryPersistenceException;
    BigDecimal getUnitLaborCost(String productType) throws FlooringMasteryPersistenceException;
    void loadProducts() throws FlooringMasteryPersistenceException;

    // Consider reformatting this in some way
    void setProductFile(String testFile);
}
