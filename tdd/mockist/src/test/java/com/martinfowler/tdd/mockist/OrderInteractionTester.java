package com.martinfowler.tdd.mockist;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class OrderInteractionTester {

    private static String TALISKER = "Talisker";

    @Mocked
    private Warehouse warehouse;

    private Order order;

    @Test
    public void testFillingRemovesInventoryIfInStock() {
        order = new Order(TALISKER, 50);

        new Expectations() {{
            warehouse.hasInventory(TALISKER, 50);
            times=2;
            result=true;
            warehouse.remove(TALISKER, 50);
            times=1;
        }};

        order.fill(warehouse);

        Assert.assertTrue(order.isFilled());
    }

    @Test
    public void testFillingDoesNotRemoveIfNotEnoughInStock() {
        order = new Order(TALISKER, 51);

        new Expectations() {{
            warehouse.hasInventory(anyString, anyInt);
            times=1;
            result=false;
        }};

        order.fill(warehouse);

        new Verifications() {{
            warehouse.remove(anyString, anyInt);
            maxTimes = 0;
        }};

        Assert.assertFalse(order.isFilled());
    }
}
