package com.sg.vendingmachine.dao;
import com.sg.vendingmachine.dto.*;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineAuditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    private VendingMachineAuditDao auditDao;

    private Map<String, Item> items = new HashMap<>();
    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";

    @Autowired
    public VendingMachineDaoFileImpl(VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    /**
     * @return
     */
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        List<Item> item_list = new ArrayList<Item>(items.values());
        List<Item> items_with_quantity = item_list.stream()
                .filter((i) -> Integer.parseInt(i.getItemQuantity()) > 0)
                .collect(Collectors.toList());
        return items_with_quantity;
    }

    /**
     * @param itemNo
     * @return
     */
    @Override
    public Item getItem(String itemNo) { return items.get(itemNo);}

    /**
     * @param itemNo
     * @return
     */
    @Override
    public Item removeItem(String itemNo) throws VendingMachinePersistenceException {
        loadInventory();
        Item removedItem = null;
        Item item = new Item(itemNo);
        writeInventory();
        return removedItem;
    }
    private Item unmarshallItem(String itemAsText){
        // 01::Snickers::1.75::22
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemNo = itemTokens[0];
        Item itemFromFile = new Item(itemNo);
        itemFromFile.setItemName(itemTokens[1]);
        itemFromFile.setItemCost(itemTokens[2]);
        itemFromFile.setItemQuantity(itemTokens[3]);
        return itemFromFile;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getItemNo(), currentItem);
        }
        scanner.close();
    }
    private String marshallItem(Item anItem){
        // 01::Snickers::1.75::22

        String itemAsText = anItem.getItemNo() + DELIMITER;
        itemAsText += anItem.getItemName() + DELIMITER;
        itemAsText += anItem.getItemCost() + DELIMITER;
        itemAsText += anItem.getItemQuantity();
        return itemAsText;
    }

    /**
     * Writes all items in inventory out to a INVENTORY_FILE.  See loadInventory
     * for file format.
     *
     * @throws VendingMachinePersistenceException if an error occurs writing to the file
     */
    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
}
