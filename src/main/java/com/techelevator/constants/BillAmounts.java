package com.techelevator.constants;

public enum BillAmounts {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10);

    private final int value;

    BillAmounts(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }



}
