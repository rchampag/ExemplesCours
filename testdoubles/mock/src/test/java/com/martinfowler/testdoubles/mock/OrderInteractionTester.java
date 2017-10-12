package com.martinfowler.testdoubles.mock;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class OrderInteractionTester {

    private static String TALISKER = "Talisker";
    private Warehouse warehouse = new WarehouseImpl();
    private Order order;

    @Mocked
    private MailService mailer;

    @Before
    public void setUp() throws Exception {
        warehouse.add(TALISKER, 50);
    }

    @Test
    public void testOrderSendsMailIfUnfilled() {
        order = new Order(TALISKER, 51);
        order.setMailer(mailer);

        new Expectations() {{
            mailer.send(withInstanceOf(Message.class)); times=1;
        }};

        order.fill(warehouse);

        Assert.assertFalse(order.isFilled());
    }
}
