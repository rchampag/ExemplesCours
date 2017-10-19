package com.martinfowler.testdoubles.stub;

import java.util.ArrayList;
import java.util.List;

public class MailServiceStub implements MailService {

    private List<Message> messages = new ArrayList<Message>();

    public void send (Message msg) {
        messages.add(msg);
    }

    public int numberSent() {
        return messages.size();
    }
}