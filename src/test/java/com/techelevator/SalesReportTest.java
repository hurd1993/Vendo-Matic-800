package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

public class SalesReportTest {

    private SalesReport salesReport;
    private Inventory inventory = new Inventory();

    @Before
    public void setUp() {
        inventory.stockInventory("vendingmachine.csv");
        salesReport = new SalesReport(inventory,"TestSales");
        File folder = new File("TestSales");
        if(folder.listFiles().length <= 0) {
            Map<String,Integer> testMap = new HashMap<>();
            testMap.put("Potato Crisps",10);
            testMap.put("Stackers",3);
            testMap.put("Grain Waves",0);
            testMap.put("Cloud Popcorn",50);
            salesReport.setSalesReport(testMap);
        }

    }

    @Test
    public void getSalesReportReturnsCorrectValuesFromInit() {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",50);

        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

    @Test
    public void getTotalSalesTest() {

        String expectedSales = "$217.35";
        Assert.assertEquals(expectedSales,salesReport.getTotalSales());

    }

    @Test
    public void getSalesReportShouldReturnStartingWithHighestQuantitySold() {

        String expected= "{Cloud Popcorn=50, Potato Crisps=10, Stackers=3, Grain Waves=0}";
        Assert.assertEquals(expected,salesReport.getSalesReport().toString());

    }

    @Test
    public void updateSalesReportWithOneItem() throws UserInputException {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",51);
        salesReport.updateSalesReport("Cloud Popcorn");
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

    @Test
    public void updateSalesReportWithTwoItems() throws UserInputException {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",11);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",51);
        salesReport.updateSalesReport("Cloud Popcorn");
        salesReport.updateSalesReport("Potato Crisps");
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }
    @Test
    public void updateSalesReportWithNewItem() throws UserInputException {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",50);
        expected.put("Cowtales",1);
        salesReport.updateSalesReport("Cowtales");
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

    @Test(expected = UserInputException.class)
    public void updateSalesReportWithNullShouldThrowException() throws UserInputException {


        salesReport.updateSalesReport(null);

    }
    @Test(expected = UserInputException.class)
    public void updateSalesReportWithUnknownItemShouldThrowException() throws UserInputException {


        salesReport.updateSalesReport("Mountain Lightning");

    }

    @Test(expected = UserInputException.class)
    public void updateSalesReportWithEmptyShouldThrowException() throws UserInputException {

        salesReport.updateSalesReport("");

    }

    @Test
    public void writeReportToFileShouldGenerateFile() {
        salesReport.writeReportToFile();
        File expectedFolder = new File("TestSales");
        Assert.assertTrue(expectedFolder.exists());
    }

}
