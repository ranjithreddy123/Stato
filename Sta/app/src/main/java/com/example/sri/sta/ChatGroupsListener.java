package com.example.sri.sta;
import org.chat21.android.core.exception.ChatRuntimeException;
import org.chat21.android.core.chat_groups.models.ChatGroup;

/**
 * Created by sri on 01-08-2018.
 */

public interface ChatGroupsListener {

    void onGroupAdded(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupChanged(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupRemoved(ChatRuntimeException e);
}