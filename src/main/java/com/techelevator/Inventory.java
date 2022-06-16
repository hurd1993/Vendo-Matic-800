package com.techelevator;

import com.techelevator.products.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private final List<VendingMachineSlot> vendingSlots = new ArrayList<>();

    public void stockInventory(String inventoryFile) {
        final String CHIP = "Chip";
        final String DRINK = "Drink";
        final String CANDY = "Candy";
        final String GUM = "Gum";
        File file = new File(inventoryFile);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] currentLine = scanner.nextLine().split("\\|");
                String currentIdentifier = currentLine[0];
                VendingMachineItem currentItem = null;
                switch (currentLine[3]) {
                    case CHIP:
                        currentItem = new Chip(currentLine[1], Double.parseDouble(currentLine[2]));
                        break;
                    case CANDY:
                        currentItem = new Candy(currentLine[1], Double.parseDouble(currentLine[2]));
                        break;
                    case DRINK:
                        currentItem = new Drink(currentLine[1], Double.parseDouble(currentLine[2]));
                        break;
                    case GUM:
                        currentItem = new Gum(currentLine[1], Double.parseDouble(currentLine[2]));
                        break;
                }

                vendingSlots.add(new VendingMachineSlot(currentIdentifier, currentItem));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory File Not Found.");
        }
    }

    public List<VendingMachineSlot> getVendingSlots() {
        return vendingSlots;
    }

    public void displayVendingMachineItems() {
        for (VendingMachineSlot vendingSlot : vendingSlots) {
            System.out.println(vendingSlot.toString());
        }
    }

    public boolean isValidSlot(String slotName) {
        boolean status = false;
        for (VendingMachineSlot slot : vendingSlots) {
            if (slot.getSlotIdentifier().equalsIgnoreCase(slotName)) {
                status = true;
                break;
            }
        }
        return status;
    }
}
