package com.martinfowler.tdd.mockist;

public class Order {
    private String description;
    private int quantity;
    private boolean isOrderFilled;

    public Order(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public void fill(Warehouse warehouse) {
        if (warehouse != null) {
            isOrderFilled = warehouse.hasInventory(description, quantity);
            if(isOrderFilled)
                warehouse.remove(description, quantity);
        }
    }

    public boolean isFilled() {
        return isOrderFilled;
    }
}