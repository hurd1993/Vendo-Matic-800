package com.techelevator;

import com.techelevator.products.VendingMachineItem;
import com.techelevator.products.VendingMachineSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SalesReport {
    private Map<String, Integer> salesReport = new HashMap<>();
    private Inventory inventory;

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
        return salesReport;
    }

    public void updateSalesReport(VendingMachineItem item) {

    }

    private void sortSalesReport() {

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
