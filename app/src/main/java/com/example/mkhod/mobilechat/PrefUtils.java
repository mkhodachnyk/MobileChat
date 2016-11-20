package com.example.mkhod.mobilechat;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mkhod on 20.11.2016.
 */

public class PrefUtils {
    public static void saveInterval(Context context, int interval) {
        SharedPreferences sPref = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sPref.edit().putInt("INTERVAL", interval).apply();
    }

    public static int getInterval(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sPref.getInt("INTERVAL", 3);
    }
}
