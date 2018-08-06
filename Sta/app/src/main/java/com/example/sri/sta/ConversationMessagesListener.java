package com.example.sri.sta;
import com.example.sri.sta.ChatRuntimeException;
import com.example.sri.sta.Message;

/**
 * Created by sri on 03-08-2018.
 */

public interface ConversationMessagesListener {

    public void onConversationMessageReceived(Message message, ChatRuntimeException e);
    public void onConversationMessageChanged(Message message, ChatRuntimeException e);

}