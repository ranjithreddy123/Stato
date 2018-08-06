package com.example.sri.sta;
import com.example.sri.sta.ChatRuntimeException;
import com.example.sri.sta.Message;

/**
 * Created by sri on 03-08-2018.
 */
public interface SendMessageListener {

    void onBeforeMessageSent(Message message, ChatRuntimeException chatException);

    void onMessageSentComplete(Message message, ChatRuntimeException chatException);
}
