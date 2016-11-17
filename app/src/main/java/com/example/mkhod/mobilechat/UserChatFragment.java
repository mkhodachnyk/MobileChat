package com.example.mkhod.mobilechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by mkhod on 16.11.2016.
 */
public class UserChatFragment extends android.support.v4.app.Fragment {

    public static ChatAdapter chatAdapter;

    private static final String ARG_USER_ID = "user_id";
    private ChatUser user;

    private EditText messageInputEditText;
    private TextView userNameTitleTextView;
    private ListView chatListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID userId = (UUID) getArguments().getSerializable(ARG_USER_ID);
        user = UserLab.getInstance(getActivity()).getUser(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);

        messageInputEditText = (EditText) view.findViewById(R.id.message_edit_text);
        messageInputEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (messageInputEditText.getRight() - messageInputEditText
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        sendMessage(messageInputEditText.getText().toString());

                        return true;
                    }
                }
                return false;
            }

        });


        userNameTitleTextView = (TextView) view.findViewById(R.id.chat_user_name_title);
        userNameTitleTextView.setText(user.getName());

        chatListView = (ListView) view.findViewById(R.id.message_list_view);
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
}
