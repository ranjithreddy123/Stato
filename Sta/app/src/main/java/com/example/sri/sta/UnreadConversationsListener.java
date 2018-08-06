package com.example.sri.sta;
import com.example.sri.sta.Conversation;
import com.example.sri.sta.ChatRuntimeException;

/**
 * Created by sri on 03-08-2018.
 */


public interface UnreadConversationsListener {

    void onUnreadConversationCounted(int count, ChatRuntimeException e);

}