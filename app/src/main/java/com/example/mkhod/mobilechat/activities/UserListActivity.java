package com.example.mkhod.mobilechat.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mkhod.mobilechat.R;
import com.example.mkhod.mobilechat.fragments.UserChatFragment;
import com.example.mkhod.mobilechat.models.UserLab;
import com.example.mkhod.mobilechat.fragments.UserListFragment;

/**
 * Created by mkhod on 15.11.2016.
 */

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            UserListFragment userListFragment =
                    (UserListFragment) fragmentManager.findFragmentById(R.id.fragment_container_list);
            if (userListFragment == null) {
                userListFragment = new UserListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_list, userListFragment)
                        .commit();
            }

            UserChatFragment userChatFragment =
                    (UserChatFragment) fragmentManager.findFragmentById(R.id.fragment_container_chat);
            if (userChatFragment == null) {
                userChatFragment = UserChatFragment.newInstance(UserLab.getInstance(this).getUsers().get(0).getId());
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_chat, userChatFragment)
                        .commit();
            }
        } else {
            UserListFragment userListFragment =
                    (UserListFragment) fragmentManager.findFragmentById(R.id.fragment_container);
            if (savedInstanceState == null || userListFragment == null) {
                userListFragment = new UserListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, userListFragment)
                        .commit();
            }
        }
    }
}
