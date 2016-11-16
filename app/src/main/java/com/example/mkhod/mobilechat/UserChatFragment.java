package com.example.mkhod.mobilechat;

import android.os.Bundle;

import java.util.UUID;

/**
 * Created by mkhod on 16.11.2016.
 */
public class UserChatFragment extends android.support.v4.app.Fragment {
    private static final String ARG_USER_ID = "user_id";

    public static UserChatFragment newInstance(UUID userId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);

        UserChatFragment fragment = new UserChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
