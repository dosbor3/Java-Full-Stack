package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao {
    private Map<LocalDate, Map<Integer, Order>> allOrdersByDate = new HashMap<>();
    private static final String DELIMITER = ",";
    private final File EXPORT_FILE, ORDER_PATH;
    private final DateTimeFormatter orderFileFormatter =
            DateTimeFormatter.ofPattern("MMddyyyy");
    private final DateTimeFormatter exportDateFormatter =
            DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private final String ORDER_FILE_PRE = "Orders_";
    private final String FILE_EXT = ".txt";
    private int newId = 1;

    public FlooringMasteryOrderDaoFileImpl() {
        ORDER_PATH = new File("Orders");
        EXPORT_FILE = new File("Backup", "DataExport.txt");
    }
    @Override
    public Order addOrder(Order order, LocalDate day) {
        order.setOrderNumber(newId);
        newId ++;
        // needs to be n+1 because starting from index 1
        if (!checkDateExists(day)) {
            allOrdersByDate.put(day, new HashMap<>());
        }
        return allOrdersByDate.get(day).put(order.getOrderNumber(), order);
    }


    private int generateOrderNo() {
        return allOrdersByDate.values().stream()
                .map(Map::keySet)
                .mapToInt(idSet -> Collections.max(idSet))
                .max().orElse(0) + 1;
    }


    /**
     * Have to be used with checkDateExists method
     * @param date to look up all orders to be delivered on that date
     * @return list of Order objects sorted by order number
     */
    @Override
    public List<Order> getOrderOnDay(LocalDate date) {
        return allOrdersByDate.get(date).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * removes order object from allOrdersMap and return it;
     * does not check or throw exception
     * HAVE TO USE with checkDateExists method
     */
    @Override
    public Order removeOrder(LocalDate date, int orderNo) {
        Order order = allOrdersByDate.get(date).remove(orderNo);
        if (allOrdersByDate.get(date).size() < 1) {
            allOrdersByDate.remove(date);
        }
        return order;
    }

    /**
     Have to be used with checkOrderExists method
     */
    @Override
    public Order editOrder(LocalDate oldDate, Order newOrder) {
        LocalDate newDate = newOrder.getDate();
        int orderNo = newOrder.getOrderNumber();
        if (oldDate.isEqual(newDate)) {
            return allOrdersByDate.get(oldDate).replace(orderNo, newOrder);
        } else if (!checkDateExists(newDate)) {
            allOrdersByDate.put(newDate, new HashMap<>());
        }
        allOrdersByDate.get(newDate).put(orderNo, newOrder);
        return removeOrder(oldDate, orderNo);
    }

    @Override
    public Order retrieveOrder(LocalDate date, int orderNo) {
        return allOrdersByDate.get(date).get(orderNo);
    }

    @Override
    public boolean checkDateExists(LocalDate date) {
        return allOrdersByDate.containsKey(date); // service layer can call this method;
    }

    @Override
    public boolean checkOrderExists(LocalDate date, int orderNo) {
        return checkDateExists(date) && allOrdersByDate.get(date).containsKey(orderNo);
    }

    @Override
    public int getTotalOrder() {
        return allOrdersByDate.values().stream()
                .mapToInt(Map::size)
                .sum();
    }

    public Order unmarshallOrder(String line, LocalDate day) {
        // constructor: public Order(LocalDate date, String customerName, String state, String productType, BigDecimal area)
        String[] args = line.split(DELIMITER);
        Order order = new Order(day, args[1], args[2], args[4], new BigDecimal(args[5]));
        order.setTaxRate(new BigDecimal(args[3]));
        order.setCost_per_sq_foot(new BigDecimal(args[6]));
        order.setLabor_cost_per_sq_foot(new BigDecimal(args[7]));
        order.setMaterialCost(new BigDecimal(args[8]));
        order.setLaborCost(new BigDecimal(args[9]));
        order.setTax(new BigDecimal(args[10]));
        order.setTotal(new BigDecimal(args[11]));
        return order;
    }
    public Order unmarshallOrder(String line) {
        String[] args = line.split(DELIMITER);
        LocalDate day = LocalDate.parse(args[12], exportDateFormatter);
        return unmarshallOrder(line, day);
    }
    /**
     * This method loads all the order records from a single order file,
     * the order file being read from stores all the orders for that date,
     * OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,
     * LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
     * @param day LocalDate object, all orders read from the file have this date
     * @param file order file to read from
     * @return Map of order number pointing to order object
     */
    public Map<Integer, Order> loadSingleDayOrders(File file, LocalDate day) throws FlooringMasteryPersistenceException {
        Scanner scanner;
        Map<Integer, Order> daysOrders = new HashMap<>();
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(file)));
        } catch (FileNotFoundException ex) {
            throw new FlooringMasteryPersistenceException(
                    "Cannot locate file to read from: " + file.getPath(), ex);
        }
        String line; scanner.nextLine(); //skip header
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            Order order = unmarshallOrder(line, day);
            addOrder(order, day);
        }
        scanner.close();
        return daysOrders;
    }

    /**
     * read all order files from the default order file folder (Orders/Orders_xxxx.txt)
     * @return the total number of orders loaded
     */
    @Override
    public int loadAllDayOrders() throws FlooringMasteryPersistenceException {
        File[] orderFiles = ORDER_PATH.listFiles();
        if (orderFiles == null) {
            throw new FlooringMasteryPersistenceException(
                    ORDER_PATH + " is empty! Cannot find order files to read from!");
        }
        LocalDate day;
        //to parse the date from file name: get the beginning index
        //and ending index of the part of file name with date;
        //int dayBeginId = ORDER_FILE_PRE.length();
        //int dayEndId = dayBeginId + orderFileFormatter.toString().length();
        int dayBeginId = 7;
        int dayEndId = 15;
        for (File file: orderFiles) {
            day = LocalDate.parse(file.getName().substring(dayBeginId, dayEndId),
                    orderFileFormatter);
            loadSingleDayOrders(file, day);
            // allOrdersByDate.put(day, daysOrders); do not need another
        }
        return getTotalOrder();
    }

    /**
     * imports order from a single backup file that stores all orders
     * has one more field compared to order files: has date of order at end.
     * @return total number of orders loaded
     */
    @Override
    public int importOrders() {
        throw new UnsupportedOperationException("-_- not implemented!");
    }

    private String marshallOrder(Order order) {
        //1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06
        String orderAsText = order.getOrderNumber() + DELIMITER;
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getCost_per_sq_foot() + DELIMITER;
        orderAsText += order.getLabor_cost_per_sq_foot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTax() + DELIMITER;
        orderAsText += order.getTotal();
        return orderAsText;
    }

    /**
     * marshalls order object to line for writing to data export file
     * @return a comma separated row entry standing for said order object
     */
    private String marshallOrder(Order order, LocalDate date) {
        return marshallOrder(order) + DELIMITER + date.format(exportDateFormatter);
    }

    /**
     * writes orders by date into default orders folder. (Orders/Orders_xxxx.txt)
     * @return total number of orders written
     * @throws FlooringMasteryPersistenceException if something wrong with writing
     */
    @Override
    public int writeAllDayOrders() throws FlooringMasteryPersistenceException {
        PrintWriter out;
        File orderFile;
        String fileName;
        for (LocalDate day : allOrdersByDate.keySet()) {
            fileName = ORDER_FILE_PRE + day.format(orderFileFormatter) + FILE_EXT;
            orderFile = new File(ORDER_PATH, fileName);
            try {
                out = new PrintWriter(new FileWriter(orderFile));
            } catch (IOException e) {
                throw new FlooringMasteryPersistenceException("Cannot write to order file: " + orderFile);
            }
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                    "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            allOrdersByDate.get(day).entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(e -> marshallOrder(e.getValue()))
                    .forEachOrdered(out::println);
            out.close();
        }
        return getTotalOrder();
    }

    /**
     * export all orders into a single backup file
     * @return total number of orders written su
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public int exportOrders() throws FlooringMasteryPersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(EXPORT_FILE));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Cannot write to export file: " + EXPORT_FILE);
        }
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
        for (LocalDate day : allOrdersByDate.keySet()) {
            allOrdersByDate.get(day).entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(e -> marshallOrder(e.getValue(), day))
                    .forEachOrdered(out::println);
            out.flush();
        }
        out.close();
        return getTotalOrder();
    }


    //TODO read states file and get the list of states available, store state name, state tax rate;
    //TODO read product type file and return list of product types available, cost per sqft and labor cost;
}
