package com.example.mkhod.mobilechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mkhod.mobilechat.PrefUtils;
import com.example.mkhod.mobilechat.R;
import com.facebook.login.LoginManager;

import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar intervalsSeekBar;
    private TextView intervalsValueTextView;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        intervalsSeekBar = (SeekBar) findViewById(R.id.seconds_interval_seek_bar);
        intervalsValueTextView = (TextView) findViewById(R.id.seconds_intervsl_text_view);
        logoutButton = (Button) findViewById(R.id.logout_button);

        intervalsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intervalsValueTextView.setText(String.valueOf(seekBar.getProgress()));
                PrefUtils.saveInterval(SettingsActivity.this, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}
