package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */

public class ChatRuntimeException extends RuntimeException {

    public ChatRuntimeException() {
        super();
    }

    public ChatRuntimeException(String message) {
        super(message);
    }

    public ChatRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRuntimeException(Throwable cause) {
        super(cause);
    }

}