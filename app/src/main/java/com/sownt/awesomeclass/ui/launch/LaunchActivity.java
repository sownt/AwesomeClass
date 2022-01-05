package com.sownt.awesomeclass.ui.launch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sownt.awesomeclass.R;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        getSupportFragmentManager().beginTransaction().add(
                R.id.launch_container,
                LoginFragment.class,
                null
        ).commit();
    }
}