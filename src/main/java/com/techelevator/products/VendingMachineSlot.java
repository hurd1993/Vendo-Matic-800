package com.techelevator.products;

public class VendingMachineSlot {
    private final String slotIdentifier;
    private final VendingMachineItem assignedItem;
    private int currentQuantity = 5;

    public VendingMachineSlot(String slotIdentifier, VendingMachineItem assignedItem) {
        this.slotIdentifier = slotIdentifier;
        this.assignedItem = assignedItem;
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }

    public VendingMachineItem getAssignedItem() {
        return assignedItem;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void reduceQuantity() {
        this.currentQuantity--;
    }

    @Override
    public String toString() {
        if (currentQuantity <= 0) {
            return slotIdentifier + " | " + assignedItem.getName() + " | " + assignedItem.getPriceAsString() + " | Quantity: SOLD OUT";

        }
        return slotIdentifier + " | " + assignedItem.getName() + " | " + assignedItem.getPriceAsString() + " | Quantity: " + currentQuantity;
    }
}
