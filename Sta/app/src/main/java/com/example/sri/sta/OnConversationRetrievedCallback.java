package com.example.sri.sta;
import com.example.sri.sta.Conversation;

/**
 * Created by sri on 03-08-2018.
 */

public interface OnConversationRetrievedCallback {

    void onConversationRetrievedSuccess(Conversation conversation);

    void onNewConversationCreated(String conversationId);

    void onConversationRetrievedError(Exception e);
}
