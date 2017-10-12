package ca.etsmtl.log430.SimpleEventExample;

import java.util.List;
import java.util.ArrayList;

// Someone who says "Hello"
public class Initiator {
    private List<HelloListener> listeners = new ArrayList<HelloListener>();

    public void addListener(HelloListener toAdd) {
        listeners.add(toAdd);
    }

    public void sayHello() {
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (HelloListener hl : listeners)
            hl.someoneSaidHello();
    }
}