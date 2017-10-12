package ca.etsmtl.log430.SimpleEventBusExample;

import com.google.common.eventbus.EventBus;

// Someone who says "Hello"
public class Initiator {

    private EventBus eventBus = null;

    public Initiator(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void sayHello() {
        eventBus.post(new HelloEvent(this, "Hello !!!"));
        System.out.println("Control returned to Initiator. Bye!");
    }
}