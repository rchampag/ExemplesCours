package ca.etsmtl.log430.SimpleExample;

// Someone who says "Hello"
public class Initiator {

    public void sayHello() {
        System.out.println("Hello!!");

        Responder01 responder01 = new Responder01();
        Responder02 responder02 = new Responder02();
        Responder03 responder03 = new Responder03();

        responder01.someoneSaidHello();
        responder02.someoneSaidHello();
        responder03.someoneSaidHello();
    }
}