package com.techelevator.products;

public class Drink extends VendingMachineItem{
    public Drink(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return "Glug Glug, Yum";
    }
}
