package com.example.mkhod.mobilechat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mkhod.mobilechat.R;
import com.example.mkhod.mobilechat.adapters.ChatAdapter;
import com.example.mkhod.mobilechat.models.ChatUser;
import com.example.mkhod.mobilechat.models.Message;
import com.example.mkhod.mobilechat.models.OnMessageSentClickEvent;
import com.example.mkhod.mobilechat.models.OnMessageSentEvent;
import com.example.mkhod.mobilechat.models.UserLab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

/**
 * Created by mkhod on 16.11.2016.
 */
public class UserChatFragment extends android.support.v4.app.Fragment {

    private static ChatAdapter chatAdapter;

    private static final String ARG_USER_ID = "user_id";
    private ChatUser user;

    private EditText messageInputEditText;


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID userId = (UUID) getArguments().getSerializable(ARG_USER_ID);
        user = UserLab.getInstance(getActivity()).getUser(userId);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);

        messageInputEditText = (EditText) view.findViewById(R.id.message_edit_text);

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageInputEditText.getText().toString().equals("")
                        && !(messageInputEditText.getText() == null)) {
                    sendMessage(messageInputEditText.getText().toString());
                    EventBus.getDefault().post(new OnMessageSentClickEvent(true));
                }
            }
        });

        TextView userNameTitleTextView = (TextView) view.findViewById(R.id.chat_user_name_title);
        userNameTitleTextView.setText(user.getName());

        ListView chatListView = (ListView) view.findViewById(R.id.message_list_view);
        chatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatListView.setStackFromBottom(true);

        chatAdapter = new ChatAdapter(getActivity(), user.getMessages());
        chatListView.setAdapter(chatAdapter);

        return view;
    }

    public static UserChatFragment newInstance(UUID userId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);

        UserChatFragment fragment = new UserChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendMessage(String text) {
        Message message = new Message(text, true);
        user.addMessage(message);
        messageInputEditText.setText("");
        chatAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnMessageSentEvent event) {
        if (event.isSent()) {
            chatAdapter.notifyDataSetChanged();
        }
    }
}
