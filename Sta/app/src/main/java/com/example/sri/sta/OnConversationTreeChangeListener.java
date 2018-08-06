package com.example.sri.sta;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import com.example.sri.sta.Conversation;

/**
 * Created by sri on 03-08-2018.
 */

public interface OnConversationTreeChangeListener {
    void onTreeDataChanged(DatabaseReference node, DataSnapshot dataSnapshot, int childrenCount);

    void onTreeChildAdded(DatabaseReference node, DataSnapshot dataSnapshot, Conversation conversation);

    void onTreeChildChanged(DatabaseReference node, DataSnapshot dataSnapshot, Conversation conversation);

    void onTreeChildRemoved();

    void onTreeChildMoved();

    void onTreeCancelled();
}