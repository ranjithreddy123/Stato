package com.example.sri.sta;
import com.example.sri.sta.IChatUser;


/**
 * Created by sri on 01-08-2018.
 */

public interface OnGroupMemberClickListener {
    void onGroupMemberClicked(IChatUser groupMember, int position);
}