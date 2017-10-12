package ca.etsmtl.log430.SimpleEventBusExample;

import com.google.common.eventbus.EventBus;

public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        // Initiator needs access to EventBus to post events
        Initiator initiator = new Initiator(eventBus);

        Responder01 responder01 = new Responder01();
        Responder02 responder02 = new Responder02();
        Responder03 responder03 = new Responder03();

        // responder01 needs to be registered with EventBus to get event notifications
        eventBus.register(responder01);
        eventBus.register(responder02);
        eventBus.register(responder03);

        initiator.sayHello();  // Prints "Hello!!!" and "Hello there..."
    }
}
