package com.example.sri.sta;
import com.example.sri.sta.ChatRuntimeException;
import com.example.sri.sta.IChatUser;

/**
 * Created by sri on 03-08-2018.
 */

public interface ContactListener {

    public void onContactReceived(IChatUser contact, ChatRuntimeException e);
    public void onContactChanged(IChatUser contact, ChatRuntimeException e);
    public void onContactRemoved(IChatUser contact, ChatRuntimeException e);

}
