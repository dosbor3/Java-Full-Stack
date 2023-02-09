package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductDao;
import com.sg.flooringmastery.model.Product;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao {
    private Map<String, Product> allProductTypes = new HashMap<>();

    public FlooringMasteryProductDaoStubImpl() {
        addProductType(new Product("Carpet",BigDecimal.valueOf(2.25),BigDecimal.valueOf(2.10)));
        addProductType(new Product("Wood",BigDecimal.valueOf(5.15),BigDecimal.valueOf(4.75)));
        addProductType(new Product("Marble",BigDecimal.valueOf(1.75), BigDecimal.valueOf(2.10)));
    }
    private void addProductType(Product product) {
        allProductTypes.put(product.getProductType(), product);
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(allProductTypes.values());
    }

    @Override
    public List<String> getAllProductTypes() {
        return new ArrayList<>(allProductTypes.keySet());
    }

    @Override
    public Product unmarshallProduct(String line) {
        return null;
    }

    @Override
    public BigDecimal getUnitMatrlCost(String productType) {
        if (!allProductTypes.containsKey(productType)) return null;
        return allProductTypes.get(productType).getCost_per_sq_foot();
    }

    @Override
    public BigDecimal getUnitLaborCost(String productType) throws FlooringMasteryPersistenceException {
        if (!allProductTypes.containsKey(productType)) return null;
        return allProductTypes.get(productType).getLabor_cost_per_sq_foot();
    }

    @Override
    public void loadProducts() throws FlooringMasteryPersistenceException {}

    @Override
    public void setProductFile(String testFile) {

    }

}
