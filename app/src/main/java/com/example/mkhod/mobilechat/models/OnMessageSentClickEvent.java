package com.example.mkhod.mobilechat.models;

/**
 * Created by mkhod on 17.11.2016.
 */

public class OnMessageSentClickEvent {
    private boolean isClicked;

    public OnMessageSentClickEvent(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isClicked() {
        return isClicked;
    }
}
