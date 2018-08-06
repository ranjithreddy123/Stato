package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */

public interface MyPresenceListener {

    /**
     * Return the user connection status
     *
     * @param isConnected true if the user is online, false otherwise
     * @param deviceId    the id associated to the connected device
     */
    void isLoggedUserOnline(boolean isConnected, String deviceId);

    void onMyPresenceError(Exception e);
}