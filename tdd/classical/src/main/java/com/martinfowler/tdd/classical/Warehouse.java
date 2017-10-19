package com.martinfowler.tdd.classical;

public interface Warehouse {
    public boolean hasInventory(String key, int quantity);
    public void add(String key, int quantity);
    public int getInventory(String key);
    void remove(String description, int quantity);
}
