package com.example.sri.sta;
import com.example.sri.sta.Conversation;
import com.example.sri.sta.ChatRuntimeException;

/**
 * Created by sri on 03-08-2018.
 */

public interface ConversationsListener {

    public void onConversationAdded(Conversation conversation, ChatRuntimeException e);

    public void onConversationChanged(Conversation conversation, ChatRuntimeException e);

    public void onConversationRemoved(ChatRuntimeException e);

}