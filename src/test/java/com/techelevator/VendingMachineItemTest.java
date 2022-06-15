package com.techelevator;

import com.techelevator.products.VendingMachineItem;
import org.junit.Assert;
import org.junit.Test;

public class VendingMachineItemTest {
    @Test
    public void itemPriceShouldConvertToProperFormat() {
        VendingMachineItem item = new VendingMachineItem("Potato Crisps",1.25);
        String expected = "$1.25";
        Assert.assertEquals(expected,item.getPriceAsString());
    }
    @Test
    public void itemPriceShouldConvertToProperFormatGivenAnInteger() {
        VendingMachineItem item = new VendingMachineItem("Potato Crisps",1);
        String expected = "$1.00";
        Assert.assertEquals(expected,item.getPriceAsString());
    }
}
