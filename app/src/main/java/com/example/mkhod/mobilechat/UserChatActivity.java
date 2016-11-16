package com.example.mkhod.mobilechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

/**
 * Created by mkhod on 16.11.2016.
 */

public class UserChatActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "com.example.mkhod.mobilechat.user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            UUID userId = (UUID) getIntent().getSerializableExtra(EXTRA_USER_ID);
            fragment = UserChatFragment.newInstance(userId);
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public static Intent newIntent(Context packageContext, UUID userId) {
        Intent intent = new Intent(packageContext, UserChatActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

}
