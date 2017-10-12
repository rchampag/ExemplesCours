package ca.etsmtl.log430.SimpleEventExample;

// Someone interested in "Hello" events
public class Responder01 implements HelloListener {
    @Override
    public void someoneSaidHello() {
        System.out.println("Hello there...");
    }
}
