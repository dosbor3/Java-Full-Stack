package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.State;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.io.*;;
import java.util.*;

@Component
public class FlooringMasteryStateDaoFileImpl implements FlooringMasteryStateDao {
    private Map<String, State> stateList = new HashMap<>();
    private static final String DELIMITER = ",";
    private File TAX_FILE = new File("Data", "Taxes.txt");

    public List<State> getAllStates() throws FlooringMasteryPersistenceException {
        loadStates();
        return new ArrayList(stateList.values());
    }

    @Override
    public List<String> getAllStateAbbrv() throws FlooringMasteryPersistenceException {
        loadStates();
        return new ArrayList(stateList.keySet());
    }

    public State unmarshallState(String line) {
        // constructor: public Order(LocalDate date, String customerName, String state, String productType, BigDecimal area)
        String[] args = line.split(DELIMITER);
        return new State(args[0], args[1], new BigDecimal(args[2]));
    }


    @Override
    public BigDecimal getTaxRate(String stateAbbrv) throws FlooringMasteryPersistenceException {
        loadStates();
        return new BigDecimal(stateList.get(stateAbbrv).getTaxRate().toString());
    }

    @Override
    public void loadStates() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Cannot locate file to read from: " + TAX_FILE.getPath(), e);
        }
        String line = scanner.nextLine();
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            State state = unmarshallState(line);
            stateList.put(state.getStateAbbrv(), state);
        }
        scanner.close();
    }

    @Override
    public void setFilePath(String filePath) {
        this.TAX_FILE = new File(filePath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlooringMasteryStateDaoFileImpl that = (FlooringMasteryStateDaoFileImpl) o;
        return Objects.equals(stateList, that.stateList) && Objects.equals(TAX_FILE, that.TAX_FILE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateList, TAX_FILE);
    }
}
