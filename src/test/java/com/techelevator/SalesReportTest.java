package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

public class SalesReportTest {

    private SalesReport salesReport;
    private Inventory inventory = new Inventory();

    @Before
    public void setUp() {
        inventory.stockInventory("vendingmachine.csv");
        salesReport = new SalesReport(new File("SalesReportTest.txt"),inventory);
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

        double expectedSales = 217.35;
        Assert.assertEquals(expectedSales,salesReport.getTotalSales(),0);

    }

    @Test
    public void getSalesReportShouldReturnStartingWithHighestQuantitySold() {

        String expected= "{Cloud Popcorn=50, Potato Crisps=10, Stackers=3, Grain Waves=0}";
        Assert.assertEquals(expected,salesReport.getSalesReport().toString());

    }

    @Test
    public void updateSalesReportWithOneItem() {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",51);
        salesReport.updateSalesReport("Cloud Popcorn");
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

    @Test
    public void updateSalesReportWithTwoItems() {

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
    public void updateSalesReportWithNullShouldMatchOriginal() {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",50);
        salesReport.updateSalesReport(null);
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

    @Test
    public void updateSalesReportWithNEmptyShouldMatchOriginal() {

        Map<String,Integer> expected = new HashMap<>();
        expected.put("Potato Crisps",10);
        expected.put("Stackers",3);
        expected.put("Grain Waves",0);
        expected.put("Cloud Popcorn",50);
        salesReport.updateSalesReport("");
        Assert.assertThat(salesReport.getSalesReport(),is(expected));

    }

}
