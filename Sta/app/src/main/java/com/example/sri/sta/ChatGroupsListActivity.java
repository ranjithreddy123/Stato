package com.example.sri.sta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.sri.sta.R;
import com.example.sri.sta.ChatGroup;
import com.example.sri.sta.Message;
import com.example.sri.sta.ChatUser;
import com.example.sri.sta.IChatUser;
import com.example.sri.sta.ChatUI;
import com.example.sri.sta.ChatGroupsListFragment;
import com.example.sri.sta.OnGroupClickListener;
import com.example.sri.sta.MessageListActivity;

import static com.example.sri.sta.BUNDLE_CHANNEL_TYPE;
public class ChatGroupsListActivity extends AppCompatActivity implements OnGroupClickListener {
    private static final String TAG = ChatGroupsListActivity.class.getSimpleName();

    private ChatGroupsListFragment contactsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_groups_list);

        contactsListFragment = new ChatGroupsListFragment();
        contactsListFragment.setOnChatGroupClickListener(this);

        // #### BEGIN TOOLBAR ####
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // #### END  TOOLBAR ####

        // #### BEGIN CONTAINER ####
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, contactsListFragment)
                .commit();
        // #### BEGIN CONTAINER ####
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "ContactListActivity.onOptionsItemSelected");

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupClicked(ChatGroup chatGroup, int position) {
        IChatUser groupRecipient = new ChatUser(chatGroup.getGroupId(), chatGroup.getName());

        // start the message list activity
        Intent intent = new Intent(ChatGroupsListActivity.this, MessageListActivity.class);
        intent.putExtra(ChatUI.BUNDLE_RECIPIENT, groupRecipient);
        intent.putExtra(BUNDLE_CHANNEL_TYPE, Message.GROUP_CHANNEL_TYPE);
        startActivity(intent);
        finish();
    }
}