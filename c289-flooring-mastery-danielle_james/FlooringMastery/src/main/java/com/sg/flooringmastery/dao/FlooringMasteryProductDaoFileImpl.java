package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.io.*;
import java.util.*;

@Component
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {
    private Map<String, Product> products = new HashMap<>();
    private static final String DELIMITER = ",";
    private File PRODUCT_FILE = new File("Data", "Products.txt");

    // We might want to consider renaming this constant later or handling this a different way
    public void setProductFile(String filePath) {
        this.PRODUCT_FILE = new File(filePath);
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryPersistenceException {
        loadProducts();
        return new ArrayList(products.values());
    }

    @Override
    public List<String> getAllProductTypes() throws FlooringMasteryPersistenceException {
        loadProducts();
        return new ArrayList(products.keySet());
    }

    public Product unmarshallProduct(String line) {
        String[] args = line.split(DELIMITER);
        return new Product(args[0], new BigDecimal(args[1]), new BigDecimal(args[2]));
    }

    @Override
    //Return the second token from file
    public BigDecimal getUnitMatrlCost(String productType) throws FlooringMasteryPersistenceException {
        loadProducts();
        return new BigDecimal(products.get(productType).getCost_per_sq_foot().toString());
    }

    @Override
    public BigDecimal getUnitLaborCost(String productType) throws FlooringMasteryPersistenceException {
        loadProducts();
        return new BigDecimal(products.get(productType).getLabor_cost_per_sq_foot().toString());

    }

    @Override
    public void loadProducts() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Cannot locate file to read from: " + PRODUCT_FILE.getPath(), e);
        }
        String line = scanner.nextLine();
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            Product product = unmarshallProduct(line);
            products.put(product.getProductType(), product);
        }
        scanner.close();
    }


}
