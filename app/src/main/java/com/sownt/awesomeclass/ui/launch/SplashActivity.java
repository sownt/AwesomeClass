package com.sownt.awesomeclass.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sownt.awesomeclass.MainActivity;
import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.adapter.PreferenceAdapter;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash);
        new Handler().postDelayed(this::getData, 1500);
    }

    private void getData() {
        PreferenceAdapter adapter = new PreferenceAdapter(this);
        if (adapter.getMode() == PreferenceAdapter.MODE_LOCAL) {
            getDataFromLocal();
        } else {
            getDataFromFirebase();
        }
    }

    private void getDataFromLocal() {
        // TODO
    }

    private void getDataFromFirebase() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, LaunchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Log.d("AwesomeClass", "Login successful.");
        }
    }
}