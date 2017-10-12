package com.martinfowler.testdoubles.stub;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderStateTester {
    private static String TALISKER = "Talisker";
    private Warehouse warehouse = new WarehouseImpl();

    @Before
    public void setUp() throws Exception {
        warehouse.add(TALISKER, 50);
    }

    @Test
    public void testOrderSendsMailIfUnfilled() {
        Order order = new Order(TALISKER, 51);
        MailServiceStub mailer = new MailServiceStub();
        order.setMailer(mailer);
        order.fill(warehouse);
        Assert.assertEquals(1, mailer.numberSent());
    }
}
