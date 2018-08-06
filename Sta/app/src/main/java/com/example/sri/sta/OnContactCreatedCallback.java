package com.example.sri.sta;
import com.example.sri.sta.ChatRuntimeException;

/**
 * Created by sri on 03-08-2018.
 */

public interface OnContactCreatedCallback {
    void onContactCreatedSuccess(ChatRuntimeException exception);
}