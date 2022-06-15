package com.techelevator.products;

import java.text.NumberFormat;

public class VendingMachineItem {
    private String name;
    private double price;
    private final NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public VendingMachineItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceAsString() {
        return formatter.format(price);
    }
}
