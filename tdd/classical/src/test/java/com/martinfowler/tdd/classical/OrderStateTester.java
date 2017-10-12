package com.martinfowler.tdd.classical;

import com.martinfowler.tdd.classical.Order;
import com.martinfowler.tdd.classical.Warehouse;
import com.martinfowler.tdd.classical.WarehouseImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderStateTester {
    private static String TALISKER = "Talisker";
    private static String HIGHLAND_PARK = "Highland Park";
    private Warehouse warehouse = new WarehouseImpl();

    @Before
    public void setUp() throws Exception {
        warehouse.add(TALISKER, 50);
        warehouse.add(HIGHLAND_PARK, 25);
    }

    @Test
    public void testOrderIsFilledIfEnoughInWarehouse() {
        Order order = new Order(TALISKER, 50);
        order.fill(warehouse);
        Assert.assertTrue(order.isFilled());
        Assert.assertEquals(0, warehouse.getInventory(TALISKER));
    }

    @Test
    public void testOrderDoesNotRemoveIfNotEnough() {
        Order order = new Order(TALISKER, 51);
        order.fill(warehouse);
        Assert.assertFalse(order.isFilled());
        Assert.assertEquals(50, warehouse.getInventory(TALISKER));
    }
}
