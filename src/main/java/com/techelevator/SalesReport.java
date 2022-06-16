package com.techelevator;

import com.techelevator.products.VendingMachineItem;
import com.techelevator.products.VendingMachineSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SalesReport {
    private Map<String, Integer> salesReport = new HashMap<>();
    private Inventory inventory;
    private final File outputFile = new File("SalesLogs/");

    public SalesReport(File inputFile, Inventory inventory) {
        this.inventory = inventory;
        if(inputFile.exists()) {
            try(Scanner scanner = new Scanner(inputFile)) {
                while (scanner.hasNextLine()) {
                    String[] currentLine = scanner.nextLine().split("\\|");
                    salesReport.put(currentLine[0], Integer.parseInt(currentLine[1]));
                }
            }catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public Map<String, Integer> getSalesReport() {
        sortSalesReport();
        return salesReport;
    }

    public void updateSalesReport(String itemName) {
        if(salesReport.containsKey(itemName)) {
            salesReport.put(itemName,salesReport.get(itemName)+1);
        }
    }

    private void sortSalesReport() {
        List<Map.Entry<String,Integer>> list = new LinkedList<>(salesReport.entrySet());

        //Sorts the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        //Reverse The Order so most sold Item is at top
        Collections.reverse(list);

        //Assign list elements to a temporary Hashmap
        HashMap<String,Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Integer> entry : list) {
            temp.put(entry.getKey(),entry.getValue());
        }
        salesReport = temp;

    }

    public void writeReportToFile(File outputFile) {

    }

    public double getTotalSales() {

        double total = 0;
        for(Map.Entry<String,Integer> entry: salesReport.entrySet()) {
            for(VendingMachineSlot vendingMachineSlot : inventory.getVendingSlots()) {
                VendingMachineItem currentItem = vendingMachineSlot.getAssignedItem();
                if(entry.getKey().equalsIgnoreCase(currentItem.getName())) {
                    total += (currentItem.getPrice() * entry.getValue());
                }
            }

        }
        return total;
    }

}
