package com.example.mkhod.mobilechat;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mkhod on 15.11.2016.
 */

public class UserLab {
    private static UserLab userLab;

    private List<ChatUser> users;

    public static UserLab getInstance(Context context) {
        if (userLab == null)
            userLab = new UserLab(context);
        return userLab;
    }

    public List<ChatUser> getUsers() {
        return users;
    }

    public ChatUser getUser(UUID id) {
        for (ChatUser user : users)
            if (user.getId().equals(id))
                return user;
        return null;
    }

    public void addUser(ChatUser user) {
        users.add(user);
    }

    private UserLab(Context context) {
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ChatUser user = new ChatUser("User " + i);
            user.addMessage(new Message("What's up?", false));
            users.add(user);
        }
    }
}
