package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryStateDao;
import com.sg.flooringmastery.model.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringMasteryStateDaoStubImpl implements FlooringMasteryStateDao {
    private Map<String, State> allStates = new HashMap<>();

    public FlooringMasteryStateDaoStubImpl() {
        addState(new State("WA", "Washington",BigDecimal.valueOf(9.25)));
        addState(new State("TX", "Texas", BigDecimal.valueOf(4.45)));
        addState(new State("KY", "Kentucky", BigDecimal.valueOf(6.00)));
        addState(new State("CA", "Calfornia", BigDecimal.valueOf(25.00)));
    }

    private void addState(State state) {
        allStates.put(state.getStateAbbrv(), state);
    }

    @Override
    public List<String> getAllStateAbbrv() {
        return new ArrayList<>(allStates.keySet());
    }

    @Override
    public List<State> getAllStates() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(allStates.values());
    }

    @Override
    public State unmarshallState(String line) {
        return null;
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbrv) {
        return allStates.get(stateAbbrv).getTaxRate();
    }

    @Override
    public void loadStates() throws FlooringMasteryPersistenceException {

    }

    @Override
    public void setFilePath(String filePath) {

    }
}
