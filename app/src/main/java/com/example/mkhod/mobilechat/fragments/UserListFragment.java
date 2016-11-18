package com.example.mkhod.mobilechat.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mkhod.mobilechat.R;
import com.example.mkhod.mobilechat.activities.MainActivity;
import com.example.mkhod.mobilechat.models.ChatUser;
import com.example.mkhod.mobilechat.models.OnMessageSentClickEvent;
import com.example.mkhod.mobilechat.models.UserLab;
import com.facebook.login.LoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by mkhod on 15.11.2016.
 */

public class UserListFragment extends Fragment {
    private boolean dualPane;
    ChatUser selectedUser;

    private RecyclerView userRecyclerView;
    private UserAdapter adapter;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        userRecyclerView = (RecyclerView) view.findViewById(R.id.user_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        setUpFragmentStackListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            dualPane = true;
        } else dualPane = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_user_list_menu, menu);
    }

    private void updateUI() {
        UserLab userLab = UserLab.getInstance(getActivity());
        List<ChatUser> users = userLab.getUsers();
        if (adapter == null) {
            adapter = new UserAdapter(users);
            userRecyclerView.setAdapter(adapter);
        } else adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("UserListFragment", "onResume()");
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_menu_item:
                return true;
            case R.id.settings_menu_item:
                return true;
            case R.id.logout_menu_item:
                LoginManager.getInstance().logOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userNameTextView;
        private TextView messageTextView;
        private TextView dateTextView;

        private ChatUser user;

        public UserHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            userNameTextView =
                    (TextView) itemView.findViewById(R.id.list_item_user_name_text_view);
            messageTextView =
                    (TextView) itemView.findViewById(R.id.list_item_user_message_text_view);
            dateTextView =
                    (TextView) itemView.findViewById(R.id.list_item_message_date_text_view);
        }

        @Override
        public void onClick(View v) {
            selectedUser = UserLab.getInstance(getActivity()).getUser(user.getId());
            if (dualPane) {
                UserChatFragment userChatFragment = UserChatFragment.newInstance(user.getId());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_chat, userChatFragment)
                        .commit();
                adapter.notifyDataSetChanged();
            } else {
                UserChatFragment userChatFragment = UserChatFragment.newInstance(user.getId());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, userChatFragment)
                        .addToBackStack("TAG")
                        .commit();
                adapter.notifyDataSetChanged();
            }
        }

        public void bindUser(ChatUser user) {
            this.user = user;
            userNameTextView.setText(user.getName());
            messageTextView.setText(user.getLastMessage().getText());
            String dateString =
                    DateUtils.getRelativeTimeSpanString(user.getLastMessage().getDate().getTime()).toString();
            dateTextView.setText(dateString);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private List<ChatUser> users;

        public UserAdapter(List<ChatUser> users) {
            this.users = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_user, parent, false);
            return new UserHolder(view);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            ChatUser user = users.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    private void setUpFragmentStackListener() {
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (null != getActivity() && null != getActivity().getSupportFragmentManager() &&
                        null != getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container)) {

                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                    if (fragment instanceof UserListFragment) {
                        updateUI();
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnMessageSentClickEvent event) {
        if (event.isClicked()) {
            updateUI();
        }
    }
}
