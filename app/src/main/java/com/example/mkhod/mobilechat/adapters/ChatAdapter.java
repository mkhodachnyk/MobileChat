package com.example.mkhod.mobilechat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mkhod.mobilechat.models.Message;
import com.example.mkhod.mobilechat.R;

import java.util.List;

/**
 * Created by mkhod on 16.11.2016.
 */

public class ChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<Message> messages;

    public ChatAdapter(Context context, List<Message> messages) {
        this.messages = messages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messages.get(position);
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.chat_bubble, null);

        TextView messageTextView = (TextView) view.findViewById(R.id.message_text);
        messageTextView.setText(message.getText());

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.bubble_layout);

        LinearLayout parent_layout = (LinearLayout) view.findViewById(R.id.bubble_layout_parent);

        if (message.isMyMessage()) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        } else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }
        messageTextView.setTextColor(Color.BLACK);

        return view;

    }

    public void add(Message message) {
        messages.add(message);
    }

}
