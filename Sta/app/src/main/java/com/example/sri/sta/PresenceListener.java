package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */
public interface PresenceListener {

    /**
     * Return the user connection status
     *
     * @param isConnected true if the user is online, false otherwise
     */
    void isUserOnline(boolean isConnected);

    /**
     * Return the user last online timestamp
     *
     * @param lastOnline the last online timestamp
     */
    void userLastOnline(long lastOnline);

    void onPresenceError(Exception e);
}