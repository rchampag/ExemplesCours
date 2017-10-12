package ca.etsmtl.log430.SimpleEventBusExample;

import java.util.EventObject;

public class HelloEvent extends EventObject {

    private String message;

    public HelloEvent(Object source, String message) {
        super(source);
        this.message = message;
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }
}
