package com.martinfowler.testdoubles.mock;

public class Order {
    private String description;
    private int quantity;
    private boolean isOrderFilled;
    private MailService mailer;

    public Order(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public void fill(Warehouse warehouse) {
        if (warehouse != null) {
            isOrderFilled = warehouse.hasInventory(description, quantity);
            if(isOrderFilled)
                warehouse.remove(description, quantity);
            else if (mailer != null)
                mailer.send(new Message());
        }
    }

    public boolean isFilled() {
        return isOrderFilled;
    }

    public void setMailer(MailService mailer) {
        this.mailer = mailer;
    }
}