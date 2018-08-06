package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */

public class ChatUserIdException extends RuntimeException {

    public ChatUserIdException() {
        super();
    }

    public ChatUserIdException(String message) {
        super(message);
    }

    public ChatUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatUserIdException(Throwable cause) {
        super(cause);
    }

}