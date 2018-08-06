package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */

public class ChatFieldNotFoundException extends Exception {

    public ChatFieldNotFoundException() {
        super();
    }

    public ChatFieldNotFoundException(String message) {
        super(message);
    }

    public ChatFieldNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatFieldNotFoundException(Throwable cause) {
        super(cause);
    }

}