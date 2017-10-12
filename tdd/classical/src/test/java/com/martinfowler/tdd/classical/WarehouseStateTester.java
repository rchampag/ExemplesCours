package com.martinfowler.tdd.classical;

import com.martinfowler.tdd.classical.Warehouse;
import com.martinfowler.tdd.classical.WarehouseImpl;
import org.junit.Assert;
import org.junit.Test;

public class WarehouseStateTester {
    private static String TALISKER = "Talisker";

    private Warehouse warehouse = new WarehouseImpl();

    @Test
    public void testEmptyWarehouse() {
        Assert.assertEquals(0, warehouse.getInventory(TALISKER));
        Assert.assertTrue(warehouse.hasInventory(TALISKER, 0));
        Assert.assertFalse(warehouse.hasInventory(TALISKER, 1));
    }

    @Test
    public void testWarehouseAfterAddingInventory() {
        warehouse.add(TALISKER, 50);
        Assert.assertEquals(50, warehouse.getInventory(TALISKER));
        warehouse.add(TALISKER, 25);
        Assert.assertEquals(75, warehouse.getInventory(TALISKER));
        Assert.assertTrue(warehouse.hasInventory(TALISKER, 75));
        Assert.assertFalse(warehouse.hasInventory(TALISKER, 76));
    }
}