package com.techelevator.products;

public class Gum extends VendingMachineItem{
    public Gum(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return "Chew Chew, Yum";
    }
}
