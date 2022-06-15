package com.techelevator;

import com.techelevator.constants.BillAmounts;
import com.techelevator.constants.CoinAmounts;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Funds {
    private double currentFunds = 0;
    private NumberFormat format = NumberFormat.getCurrencyInstance();
    private List<Integer> validValues = new ArrayList<>();

    public double getCurrentFunds() {
        return currentFunds;
    }

    public void addFunds(double fundsToAdd) throws UserInputException {
        if (fundsToAdd <= 0) {
            throw new UserInputException("Enter A Value Greater than 0.");
        }
        currentFunds += fundsToAdd;
    }

    public void removeFunds(double fundsToRemove) throws UserInputException {
        if (fundsToRemove > currentFunds) {
            throw new UserInputException("***Insufficient Funds***");
        }
        currentFunds -= fundsToRemove;
    }

    public String toString() {
        return format.format(currentFunds);
    }

    public String getChangeBack() {
        double ogFunds = currentFunds;
        int numOfQuarters = 0;
        int numOfDimes = 0;
        int numOfNickels = 0;
        while (currentFunds / CoinAmounts.QUARTER.getValue() >= 1) {
            numOfQuarters++;
            currentFunds = Double.parseDouble(String.format("%.2f", currentFunds - CoinAmounts.QUARTER.getValue()));
        }
        while (currentFunds / CoinAmounts.DIME.getValue() >= 1) {
            numOfDimes++;
            currentFunds = Double.parseDouble(String.format("%.2f", currentFunds - CoinAmounts.DIME.getValue()));
        }
        while (currentFunds / CoinAmounts.NICKEL.getValue() >= 1) {
            numOfNickels++;
            currentFunds = Double.parseDouble(String.format("%.2f", currentFunds - CoinAmounts.NICKEL.getValue()));
        }

        AuditLog.log("GIVE CHANGE: " +
                format.format(ogFunds) + " " + format.format(currentFunds));
        return String.format("Your change is %d Quarter(s), %d Dime(s), and %d Nickel(s)", numOfQuarters, numOfDimes, numOfNickels);
    }

    public void feedFunds(String fundsToFeed) throws UserInputException {
        for (BillAmounts bill : BillAmounts.values()) {
            validValues.add(bill.getValue());
        }
        if (!validValues.contains(Integer.parseInt(fundsToFeed))) {
            throw new UserInputException("Invalid Bill Type: Please Enter 1, 2, 5, or 10");

        } else {
            for (BillAmounts value : BillAmounts.values()) {
                if (Integer.parseInt(fundsToFeed) == value.getValue()) {
                    addFunds(Integer.parseInt(fundsToFeed));
                    AuditLog.log(String.format("FEED MONEY: %s %s", format.format(Integer.parseInt(fundsToFeed)), this));
                    break;
                }
            }
        }

    }
}
