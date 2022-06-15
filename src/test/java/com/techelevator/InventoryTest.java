package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
    Inventory inv;

    @Before
    public void setUp() {
        inv = new Inventory();
        inv.stockInventory("inventoryTest.txt");
    }

    @Test
    public void inventorySizeShouldBe4AfterStocking() {

        int expectedSize = 4;
        int actualSize = inv.getVendingSlots().size();
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void properSlotNameShouldReturnTrue() {
        boolean actual = inv.isValidSlot("A1");
        Assert.assertTrue(actual);
    }
    @Test
    public void properSlotIgnoreCaseNameShouldReturnTrue() {
        boolean actual = inv.isValidSlot("a1");
        Assert.assertTrue(actual);
    }

    @Test
    public void invalidSlotNameShouldReturnFalse() {
        boolean actual = inv.isValidSlot("E1");
        Assert.assertFalse(actual);
    }

    @Test
    public void nullSlotNameShouldReturnFalse() {
        boolean actual = inv.isValidSlot(null);
        Assert.assertFalse(actual);
    }

    @Test
    public void emptySlotNameShouldReturnFalse() {
        boolean actual = inv.isValidSlot("");
        Assert.assertFalse(actual);
    }
}
