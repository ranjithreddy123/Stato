package com.example.sri.sta;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.sri.sta.ChatManager;

/**
 * Created by sri on 03-08-2018.
 */

public class ShutdownReceiver extends BroadcastReceiver {
    private static final String TAG = ShutdownReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
            Log.i(TAG, "System shutting down");

            // disconnect the current user when the phone shutdown
            if(ChatManager.getInstance() != null) {
                ChatManager.getInstance().getMyPresenceHandler().dispose();
            }
        }
    }
}