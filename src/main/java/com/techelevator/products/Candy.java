package com.techelevator.products;

public class Candy extends VendingMachineItem{
    public Candy(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return "Munch Munch, Yum";
    }
}
