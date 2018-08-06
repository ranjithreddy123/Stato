package com.example.sri.sta;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import com.example.sri.sta.R;
import com.example.sri.sta.ChatManager;
import com.example.sri.sta.ConversationsHandler;
import com.example.sri.sta.ConversationsListener;
import com.example.sri.sta.Conversation;
import com.example.sri.sta.ChatRuntimeException;
import com.example.sri.sta.MyPresenceHandler;
import com.example.sri.sta.MyPresenceListener;
import com.example.sri.sta.ChatUser;
import com.example.sri.sta.IChatUser;
import com.example.sri.sta.ChatUI;
import com.example.sri.sta.ChatGroupsListActivity;
import com.example.sri.sta.ConversationsListAdapter;
import com.example.sri.sta.OnConversationClickListener;
import com.example.sri.sta.OnConversationLongClickListener;
import com.example.sri.sta.OnSwipeMenuCloseClickListener;
import com.example.sri.sta.OnSwipeMenuUnreadClickListener;
import com.example.sri.sta.ItemDecoration;
import com.example.sri.sta.MessageListActivity;
import com.example.sri.sta.HttpManager;
import com.example.sri.sta.OnResponseRetrievedCallback;

import java.util.HashMap;
import java.util.Map;

import static com.example.sri.sta.DEBUG_MY_PRESENCE;


/**
 * Created by sri on 03-08-2018.
 */

public class ConversationListFragment extends Fragment implements
        ConversationsListener,
        OnConversationClickListener,
        OnConversationLongClickListener,
        OnSwipeMenuCloseClickListener,
        OnSwipeMenuUnreadClickListener,
        MyPresenceListener {

    private static final String TAG = ConversationListFragment.class.getName();

    private ConversationsHandler conversationsHandler;
    private MyPresenceHandler myPresenceHandler;

    // conversation list recyclerview
    private RecyclerView recyclerViewConversations;
    private LinearLayoutManager rvConversationsLayoutManager;
    private ConversationsListAdapter conversationsListAdapter;

    // no conversations layout
    private RelativeLayout noConversationsLayout;

    private FloatingActionButton addNewConversation;

    private TextView currentUserGroups;

    private HttpManager httpManager;

//    // current user presence listener
//    private OnPresenceListener onMyPresenceListener = new OnPresenceListener() {
//        @Override
//        public void onChanged(boolean imConnected) {
//            Log.d(DEBUG_MY_PRESENCE, "ConversationListFragment.onMyPresenceChange" +
//                    ".onChanged: imConnected == " + imConnected);
//        }
//
//        @Override
//        public void onLastOnlineChanged(long lastOnline) {
//            Log.d(DEBUG_MY_PRESENCE, "ConversationListFragment.onMyPresenceChange" +
//                    ".onLastOnlineChanged: lastOnline == " + lastOnline);
//        }
//
//        @Override
//        public void onMyPresenceError(Exception e) {
//            Log.e(DEBUG_MY_PRESENCE, "ConversationListFragment.onMyPresenceChange" +
//                    ".onMyPresenceError: " + e.getMessage());
//        }
//    };

    public static Fragment newInstance() {
        Fragment mFragment = new ConversationListFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        conversationsHandler = ChatManager.getInstance().getConversationsHandler();
        myPresenceHandler = ChatManager.getInstance().getMyPresenceHandler();

        httpManager = new HttpManager(getActivity());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "ConversationListFragment.onCreateView");
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);

        // init RecyclerView
        recyclerViewConversations = view.findViewById(R.id.conversations_list);
//        recyclerViewConversations.addItemDecoration(new ItemDecoration(getActivity(),
//                getResources().getDrawable(R.drawable.decorator_fragment_conversation_list)));
        recyclerViewConversations.addItemDecoration(new ItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL,
                getResources().getDrawable(R.drawable.decorator_fragment_conversation_list)));
        rvConversationsLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversations.setLayoutManager(rvConversationsLayoutManager);

        // init RecyclerView adapter
        conversationsListAdapter = new ConversationsListAdapter(
                getActivity(), conversationsHandler.getConversations());
        conversationsListAdapter.setOnConversationClickListener(this);
        conversationsListAdapter.setOnConversationLongClickListener(this);
        conversationsListAdapter.setOnSwipeMenuCloseClickListener(this);
        conversationsListAdapter.setOnSwipeMenuUnreadClickListener(this);
        recyclerViewConversations.setAdapter(conversationsListAdapter);

        // no conversations layout
        noConversationsLayout = view.findViewById(R.id.layout_no_conversations);
        toggleNoConversationLayoutVisibility(conversationsListAdapter.getItemCount());

        // add new conversations button
        addNewConversation = (FloatingActionButton) view.findViewById(R.id.button_new_conversation);
        setAddNewConversationClickBehaviour();

        currentUserGroups = view.findViewById(R.id.groups);
        showCurrentUserGroups();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "ConversationListFragment.onViewCreated");

        conversationsHandler.upsertConversationsListener(this);
        Log.d(TAG, "ConversationListFragment.onCreateView: conversationMessagesHandler attached");
        conversationsHandler.connect();

        myPresenceHandler.upsertPresenceListener(this);
        Log.d(DEBUG_MY_PRESENCE, "ConversationListFragment.onCreateView: myPresenceHandler attached");
        myPresenceHandler.connect();

//        // subscribe for current user presence changes
//        PresenceManger.observeUserPresenceChanges(ChatManager.getInstance().getTenant(),
//                ChatManager.getInstance().getLoggedUser().getId(), onMyPresenceListener);
    }

    @Override
    public void onDestroy() {

        conversationsHandler.removeConversationsListener(this);
        Log.d(TAG, "ConversationListFragment.onDestroy: conversationMessagesHandler detached");

        myPresenceHandler.removePresenceListener(this);
        Log.d(DEBUG_MY_PRESENCE, "ConversationListFragment.onDestroy: myPresenceHandler detached");

        super.onDestroy();
    }

    // check if the support account is enabled or not and assign the listener
    private void setAddNewConversationClickBehaviour() {
        Log.d(TAG, "ConversationListFragment.setAddNewConversationClickBehaviour");

        addNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChatUI.getInstance().getOnNewConversationClickListener() != null) {
                    ChatUI.getInstance().getOnNewConversationClickListener().onNewConversationClicked();
                }
            }
        });
    }

    // toggle the no conversation layout visibilty.
    // if there are items show the list of item, otherwise show a placeholder layout
    private void toggleNoConversationLayoutVisibility(int itemCount) {
        if (itemCount > 0) {
            // show the item list
            recyclerViewConversations.setVisibility(View.VISIBLE);
            noConversationsLayout.setVisibility(View.GONE);
        } else {
            // show the placeholder layout
            recyclerViewConversations.setVisibility(View.GONE);
            noConversationsLayout.setVisibility(View.VISIBLE);
        }
    }

    // show current user groups
    private void showCurrentUserGroups() {
        if (ChatUI.getInstance().areGroupsEnabled()) {
            // groups enabled
            currentUserGroups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ChatGroupsListActivity.class);
                    startActivity(intent);
                }
            });

            currentUserGroups.setVisibility(View.VISIBLE);
        } else {
            // groups not enabled

            currentUserGroups.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConversationAdded(Conversation conversation, ChatRuntimeException e) {
        // added a new conversation

        Log.d(TAG, "ConversationListFragment.onConversationAdded");

        conversationsListAdapter.notifyDataSetChanged();

        toggleNoConversationLayoutVisibility(conversationsListAdapter.getItemCount());
    }

    @Override
    public void onConversationChanged(Conversation conversation, ChatRuntimeException e) {
        // existing conversation updated

        Log.d(TAG, "ConversationListFragment.onConversationChanged");

        conversationsListAdapter.notifyDataSetChanged();

        toggleNoConversationLayoutVisibility(conversationsListAdapter.getItemCount());
    }

    @Override
    public void onConversationRemoved(ChatRuntimeException e) {
        Log.d(TAG, "ConversationListFragment.onConversationRemoved");
        if (e == null) {
            conversationsListAdapter.notifyDataSetChanged();
            toggleNoConversationLayoutVisibility(conversationsListAdapter.getItemCount());
        } else {
            Log.d(TAG, "ConversationListFragment.onConversationRemoved: " + e.toString());
        }
    }

    @Override
    public void onConversationClicked(Conversation conversation, int position) {
        // click on conversation

        // set the conversation as read
        conversationsHandler.setConversationRead(conversation.getConversationId());

        // start the message list activity of the corresponding conversation
        startMessageActivity(conversation);
    }

    @Override
    public void onConversationLongClicked(Conversation conversation, int position) {
        // long click on conversation

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        BottomSheetConversationsListFragmentLongPress dialog =
                BottomSheetConversationsListFragmentLongPress.newInstance(conversation);
        dialog.setConversationsHandler(conversationsHandler);
        dialog.show(ft, BottomSheetConversationsListFragmentLongPress.class.getName());
    }

    private void startMessageActivity(Conversation conversation) {
        Log.d(TAG, "ConversationListFragment.startMessageActivity: conversation == " + conversation.toString());

        Intent intent = new Intent(getActivity(), MessageListActivity.class);
        IChatUser recipient = new ChatUser(conversation.getConvers_with(), conversation.getConvers_with_fullname());
        intent.putExtra(ChatUI.BUNDLE_RECIPIENT, recipient);
        intent.putExtra(ChatUI.BUNDLE_CHANNEL_TYPE, conversation.getChannelType());
        getActivity().startActivity(intent);
    }

    @Override
    public void isLoggedUserOnline(boolean isConnected, String deviceId) {
        // TODO: 09/01/18
        Log.d(DEBUG_MY_PRESENCE, "ConversationListFragment.isUserOnline: " +
                "isConnected == " + isConnected + ", deviceId == " + deviceId);
    }

    @Override
    public void onMyPresenceError(Exception e) {
        // TODO: 09/01/18
        Log.e(DEBUG_MY_PRESENCE, "ConversationListFragment.onMyPresenceError: " + e.toString());
    }

    @Override
    public void onSwipeMenuClosed(Conversation conversation, int position) {
//        Log.i(TAG, "onSwipeMenuClosed: conversation: " + conversation.toString() + " position: " + position);

        String conversationId = conversation.getConversationId();
        boolean isSupportConversation = conversationId.startsWith("support-group");

        // retrieve the firebase user token
        GetTokenResult task = FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult();
        String token = task.getToken();

        // create the header parameters
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Accept", "application/json");
        headerParams.put("Content-Type", "application/json");
        headerParams.put("Authorization", "Bearer " + token);

        if (!isSupportConversation) {
            // is not support group
            deleteConversation(conversationId, headerParams);
        } else {
            // is support group
            closeSupportGroup(conversationId, headerParams);
        }

        // NOTE: dismiss is not necessary because the view disappears when the conversation is removed
//        // dismiss the swipe menu
//        conversationsListAdapter.dismissSwipeMenu(recyclerViewConversations, position);
//        conversationsListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onSwipeMenuUnread(Conversation conversation, int position) {
//        Log.i(TAG, "onSwipeMenuUnread: conversation: " + conversation.toString() + " position: " + position);

        conversationsHandler.toggleConversationRead(conversation.getConversationId());

        // dismiss the swipe menu
        conversationsListAdapter.dismissSwipeMenu(recyclerViewConversations, position);
        conversationsListAdapter.notifyItemChanged(position);
    }

    // delete a conversation form the personal timeline
    // more details availables at
    // https://github.com/chat21/chat21-cloud-functions/blob/master/docs/api.md#delete-a-conversation
    private void deleteConversation(final String conversationId, Map<String, String> headerParams) {
        Log.d(TAG, "ConversationListFragment::deleteConversation");

        // callback called when the conversation is deleted
        OnResponseRetrievedCallback<String> callback = new OnResponseRetrievedCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "ConversationListFragment::deleteConversation::" +
                        " conversation with uid " + conversationId + " deleted with success");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "ConversationListFragment::deleteConversation::" +
                        " cannot delete conversation with uid " + conversationId + ": " + e.toString());
            }
        };

        // service to call to delete the conversation
        String url = getString(R.string.chat_firebase_cloud_functions_url) +
                "/api/" + ChatManager.getInstance().getAppId() +
                "/conversations/" + conversationId;

        // perform remote deletion
        httpManager.makeHttpDELETECall(callback, url, headerParams, "");

        // perform deletion in memory
        conversationsHandler.deleteConversationFromMemory(conversationId);
    }

    // close the support group
    // more details availables at
    // https://github.com/chat21/chat21-cloud-functions/blob/master/docs/api.md#close-support-group
    private void closeSupportGroup(final String groupId, Map<String, String> headerParams) {
        Log.d(TAG, "ConversationListFragment::closeSupportGroup");

        // callback called when the group is closed
        OnResponseRetrievedCallback<String> callback = new OnResponseRetrievedCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "ConversationListFragment::closeSupportGroup::" +
                        " group with uid " + groupId + " closed with success");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "ConversationListFragment::closeSupportGroup::" +
                        " cannot close group with uid " + groupId + ": " + e.toString());
            }
        };

        // service to call to close the group
        String url = getString(R.string.chat_firebase_cloud_functions_url) + "/supportapi/" +
                ChatManager.getInstance().getAppId() + "/groups/" + groupId;

        // perform remote deletion
        httpManager.makeHttpPUTCall(callback, url, headerParams, "");

        // perform deletion in memory
        conversationsHandler.deleteConversationFromMemory(groupId);
    }
}
