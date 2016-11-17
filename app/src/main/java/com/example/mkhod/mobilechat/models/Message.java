package com.example.mkhod.mobilechat.models;

import java.util.Date;

/**
 * Created by mkhod on 15.11.2016.
 */

public class Message {
    private Date date;
    private String text;
    private boolean myMessage;

    public Message(String text, boolean myMessage) {
        this.date = new Date();
        this.text = text;
        this.myMessage = myMessage;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMyMessage() {
        return myMessage;
    }

}
