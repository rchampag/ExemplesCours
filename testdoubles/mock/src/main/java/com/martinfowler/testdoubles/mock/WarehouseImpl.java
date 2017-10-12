package com.martinfowler.testdoubles.mock;

import java.util.HashMap;

public class WarehouseImpl implements Warehouse {
    private HashMap<String, Integer> inventory = new HashMap<String, Integer>();

    public boolean hasInventory(String key, int quantity) {
        return getInventory(key) >= quantity;
    }

    public void add(String key, int quantity) {
        if (inventory.containsKey(key))
            inventory.put(key, inventory.get(key) + quantity);
        else
            inventory.put(key,quantity);
    }

    public int getInventory(String key) {
        if (inventory.containsKey(key))
            return inventory.get(key);

        return 0;
    }

    public void remove(String key, int quantity) {
        if (hasInventory(key, quantity))
            inventory.put(key, inventory.get(key) - quantity);
    }
}