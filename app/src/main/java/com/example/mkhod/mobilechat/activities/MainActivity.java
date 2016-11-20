package com.example.mkhod.mobilechat.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mkhod.mobilechat.MyService;
import com.example.mkhod.mobilechat.R;
import com.example.mkhod.mobilechat.fragments.UserChatFragment;
import com.example.mkhod.mobilechat.fragments.UserListFragment;
import com.example.mkhod.mobilechat.models.UserLab;

import java.util.UUID;

/**
 * Created by mkhod on 15.11.2016.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyService.class);
        if (!isMyServiceRunning(MyService.class)) {
            startService(intent);
        }

        UUID uuid = (UUID) getIntent().getSerializableExtra(MyService.EXTRA_MESSAGE_INFO);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (uuid != null) {
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
                    userChatFragment = UserChatFragment.newInstance(uuid);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_chat, userChatFragment)
                            .commit();
                }
            } else {

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
            }
        } else {
            if (uuid != null) {
                UserChatFragment userChatFragment = UserChatFragment.newInstance(uuid);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, userChatFragment)
                        .commit();
            } else {
                Fragment userListFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                if (savedInstanceState == null || userListFragment == null) {
                    userListFragment = new UserListFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, userListFragment)
                            .commit();
                }
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
