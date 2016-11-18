package com.example.mkhod.mobilechat.models;

/**
 * Created by mkhod on 18.11.2016.
 */

public class OnMessageSentEvent {
    private boolean isSent;

    public boolean isSent() {
        return isSent;
    }

    public OnMessageSentEvent(boolean isSent) {
        this.isSent = isSent;
    }
}
