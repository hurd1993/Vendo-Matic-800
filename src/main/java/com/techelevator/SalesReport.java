package com.techelevator;

import com.techelevator.products.VendingMachineItem;
import com.techelevator.products.VendingMachineSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SalesReport {
    private Map<String, Integer> salesReport = new HashMap<>();
    private final Inventory inventory;
    private final String salesFolder;


    public SalesReport(Inventory inventory, String salesFolder) {
        this.inventory = inventory;
        this.salesFolder = salesFolder;
        populateSalesReportWithMostRecentLog();


    }

    private void populateSalesReportWithMostRecentLog() {
        File mostRecentFile = new File("");
        File folder = new File(this.salesFolder);
        if(!folder.exists()) {
            folder.mkdir();
        }
        if(folder.listFiles().length > 0) {
            mostRecentFile = folder.listFiles()[folder.listFiles().length - 1];
        }
        if (mostRecentFile.exists()) {
            try (Scanner scanner = new Scanner(mostRecentFile)) {
                while (scanner.hasNextLine()) {
                    String currentLine = scanner.nextLine();
                    if(currentLine.isEmpty()) {
                        break;
                    }
                    String[] currentItemToAdd = currentLine.split("\\|");
                    salesReport.put(currentItemToAdd[0], Integer.parseInt(currentItemToAdd[1]));
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("Improper Value in most recent .txt file.");
            }
        }
    }

    public Map<String, Integer> getSalesReport() {
        sortSalesReport();
        return salesReport;
    }

    public void setSalesReport(Map<String,Integer> salesReport) {
        this.salesReport = salesReport;
    }

    public void updateSalesReport(String itemName) {
        if (salesReport.containsKey(itemName)) {
            salesReport.put(itemName, salesReport.get(itemName) + 1);
        }
        else {
            salesReport.put(itemName,1);
        }
    }

    private void sortSalesReport() {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(salesReport.entrySet());

        //Sorts the list
        list.sort(Map.Entry.comparingByValue());

        //Reverse The Order so most sold Item is at top
        Collections.reverse(list);

        //Assign list elements to a temporary Hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            temp.put(entry.getKey(), entry.getValue());
        }
        salesReport = temp;

    }

    public void writeReportToFile() {

        File directory = new File(salesFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
        File file = new File(directory + "/" + dateFormat.format(date) + ".txt");

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            for (Map.Entry<String, Integer> entry : getSalesReport().entrySet()) {
                writer.println(entry.getKey() + "|" + entry.getValue());

            }
            writer.println("\nTotal Sales: " + getTotalSales());
            file.setReadOnly();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public String getTotalSales() {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        double total = 0;
        for (Map.Entry<String, Integer> entry : salesReport.entrySet()) {
            for (VendingMachineSlot vendingMachineSlot : inventory.getVendingSlots()) {
                VendingMachineItem currentItem = vendingMachineSlot.getAssignedItem();
                if (entry.getKey().equalsIgnoreCase(currentItem.getName())) {
                    total += (currentItem.getPrice() * entry.getValue());
                }
            }

        }
        return numberFormat.format(total);
    }

}
