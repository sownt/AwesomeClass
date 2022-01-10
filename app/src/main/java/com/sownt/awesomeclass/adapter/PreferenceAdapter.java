package com.sownt.awesomeclass.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferenceAdapter {
    public static final int MODE_SYNC = 0;
    public static final int MODE_LOCAL = 1;

    private Context context;
    private SharedPreferences preferences;

    public PreferenceAdapter(@NonNull Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    public void setMode(int mode) {
        preferences.edit().putInt("mode", mode).apply();
    }

    public int getMode() {
        return preferences.getInt("mode", MODE_SYNC);
    }
}
