package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FundsTest {
    Funds funds;

    @Before
    public void setUp() {
        funds = new Funds();
    }

    @Test(expected = UserInputException.class)
    public void negativeFundsToAddThrowsException() throws UserInputException {
        double negFunds = -5;
        funds.addFunds(negFunds);

    }
    @Test(expected = UserInputException.class)
    public void throwExceptionWhenFundsToRemoveIsGreaterThanCurrentFunds() throws UserInputException {
        double fundsToAdd = 2;
        double fundsToRemove = 5;
        funds.addFunds(fundsToAdd);
        funds.removeFunds(fundsToRemove);

    }

    @Test(expected = UserInputException.class)
    public void zeroFundsToAddThrowsException() throws UserInputException {
        double zeroFunds = 0;
        funds.addFunds(zeroFunds);

    }

    @Test
    public void fundsBecome1_35AfterAdding() throws UserInputException {
        double fundsToAdd = 1.35;
        funds.addFunds(fundsToAdd);
        Assert.assertEquals(fundsToAdd, funds.getCurrentFunds(), 0.0);
    }

    @Test
    public void fundsBecome1AfterAddingInteger() throws UserInputException {
        int fundsToAdd = 1;
        funds.addFunds(fundsToAdd);
        Assert.assertEquals(fundsToAdd, funds.getCurrentFunds(), 0.0);
    }

    @Test
    public void fundsBecome1AfterRemoving() throws UserInputException {
        double fundsToAdd = 2;
        double fundsToRemove = 1;
        funds.addFunds(fundsToAdd);
        funds.removeFunds(fundsToRemove);
        double expected = 1;
        Assert.assertEquals(expected, funds.getCurrentFunds(), 0.0);
    }

    @Test
    public void fundsBecome1AfterRemovingIntegerFromDouble() throws UserInputException {
        double fundsToAdd = 2.5;
        double fundsToRemove = 1;
        funds.addFunds(fundsToAdd);
        funds.removeFunds(fundsToRemove);
        double expected = 1.5;
        Assert.assertEquals(expected, funds.getCurrentFunds(), 0.0);
    }

    @Test
    public void toStringMethodReturnsStringInCurrency() throws UserInputException {
        double fundsToAdd = 3.55;
        funds.addFunds(fundsToAdd);
        String expected = "$3.55";
        Assert.assertEquals(expected, funds.toString());
    }

    @Test
    public void toStringMethodReturnsStringInCurrencyWhenGivenInteger() throws UserInputException {
        int fundsToAdd = 5;
        funds.addFunds(fundsToAdd);
        String expected = "$5.00";
        Assert.assertEquals(expected, funds.toString());
    }

    @Test
    public void getChangeBackWith0_30ShouldBe1QuarterAnd1Nickel() throws UserInputException {
        double fundsToAdd = 0.30;
        funds.addFunds(fundsToAdd);
        String expected = "Your change is 1 Quarter(s), 0 Dime(s), and 1 Nickel(s)";
        Assert.assertEquals(expected, funds.getChangeBack());
    }
    @Test
    public void getChangeBackWith0_40ShouldBe1Quarter1Dime1Nickel() throws UserInputException {
        double fundsToAdd = 0.40;
        funds.addFunds(fundsToAdd);
        String expected = "Your change is 1 Quarter(s), 1 Dime(s), and 1 Nickel(s)";
        Assert.assertEquals(expected, funds.getChangeBack());
    }
    @Test
    public void getChangeBackWith1_75ShouldBe7Quarters() throws UserInputException {
        double fundsToAdd = 1.75;
        funds.addFunds(fundsToAdd);
        String expected = "Your change is 7 Quarter(s), 0 Dime(s), and 0 Nickel(s)";
        Assert.assertEquals(expected, funds.getChangeBack());
    }

    @Test
    public void testFeedFunds() throws UserInputException {
        String fundsToFeed = "1";
        funds.feedFunds(fundsToFeed);
        String expected = "$1.00";
        Assert.assertEquals(expected, funds.toString());
    }
}
