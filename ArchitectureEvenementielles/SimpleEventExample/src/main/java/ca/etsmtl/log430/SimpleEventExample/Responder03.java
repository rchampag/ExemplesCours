package ca.etsmtl.log430.SimpleEventExample;

// Someone interested in "Hello" events
public class Responder03 implements HelloListener {
    @Override
    public void someoneSaidHello() {
        System.out.println("Hello there from number 3...");
    }
}
