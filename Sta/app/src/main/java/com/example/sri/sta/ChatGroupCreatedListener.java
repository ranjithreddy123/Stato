package com.example.sri.sta;
import org.chat21.android.core.chat_groups.models.ChatGroup;
import org.chat21.android.core.exception.ChatRuntimeException;


/**
 * Created by sri on 01-08-2018.
 */

public interface ChatGroupCreatedListener {
    void onChatGroupCreated(ChatGroup chatGroup, ChatRuntimeException chatException);
}