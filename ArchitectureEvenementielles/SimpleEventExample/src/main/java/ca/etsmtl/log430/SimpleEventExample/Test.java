package ca.etsmtl.log430.SimpleEventExample;

public class Test {
    public static void main(String[] args) {
        Initiator initiator = new Initiator();

        initiator.addListener(new Responder01());
        initiator.addListener(new Responder02());
        initiator.addListener(new Responder03());

        initiator.sayHello();  // Prints "Hello!!!" and "Hello there..."
    }
}
