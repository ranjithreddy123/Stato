package com.example.sri.sta;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import com.example.sri.sta.IChatUser;


/**
 * Created by sri on 03-08-2018.
 */

public class ContactsFirestoreSynchronizer {
    FirebaseFirestore contactStore;
    CollectionReference contactsCollReference;

    private static final String TAG = ContactsFirestoreSynchronizer.class.getName();

    public ContactsFirestoreSynchronizer() {
        contactStore = FirebaseFirestore.getInstance();

        contactsCollReference = contactStore.collection("contacts");
    }

    public List<IChatUser> getAllContacts() {
        final List<IChatUser> contacts = new ArrayList<>();

        contactsCollReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
//                        contacts.add(document.toObject(ChatUser.class));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return  null;

    }
}