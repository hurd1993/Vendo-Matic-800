package com.techelevator.products;

public class Chip extends VendingMachineItem{
    public Chip(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return "Crunch Crunch, Yum";
    }
}
