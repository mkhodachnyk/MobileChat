package com.example.mkhod.mobilechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.List;

/**
 * Created by mkhod on 15.11.2016.
 */

public class UserListFragment extends Fragment {

    private RecyclerView userRecyclerView;
    private UserAdapter adapter;

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

        return view;
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
            Intent intent = UserChatActivity.newIntent(getActivity(), user.getId());
            startActivity(intent);
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


}
