package com.example.mkhod.mobilechat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mkhod.mobilechat.PrefUtils;
import com.example.mkhod.mobilechat.R;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar intervalsSeekBar;
    private TextView intervalsValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        intervalsSeekBar = (SeekBar) findViewById(R.id.seconds_interval_seek_bar);
        intervalsValueTextView = (TextView) findViewById(R.id.seconds_intervsl_text_view);

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

    }
}
