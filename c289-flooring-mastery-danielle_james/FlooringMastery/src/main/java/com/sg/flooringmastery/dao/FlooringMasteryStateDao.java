package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.State;

import java.math.BigDecimal;
import java.util.*;

public interface FlooringMasteryStateDao {
    List<String> getAllStateAbbrv() throws FlooringMasteryPersistenceException;

    List<State> getAllStates() throws FlooringMasteryPersistenceException;
    State unmarshallState(String line);
    BigDecimal getTaxRate(String stateAbbrv) throws FlooringMasteryPersistenceException;
    void loadStates() throws FlooringMasteryPersistenceException;

    public void setFilePath(String filePath);
}
