package com.ngpmbewetu.call_spyware;

public interface MessageListener {

    void messageReceived(String body);
    void originatingNumber (String sender);
    void DisplayMessagebody (String displayBody);
}
