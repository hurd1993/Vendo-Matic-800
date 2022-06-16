package com.techelevator.constants;

public enum CoinAmounts {
    NICKEL(.05),
    DIME(.10),
    QUARTER(.25);

    private final double value;

    CoinAmounts(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
