package com.sownt.awesomeclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sownt.awesomeclass.adapter.FragmentAdapter;
import com.sownt.awesomeclass.interfaces.Callback;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_PAGES = 2;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private FragmentStateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.main_pager);
        tabLayout = findViewById(R.id.main_tab);
        adapter = new FragmentAdapter(getSupportFragmentManager(), this.getLifecycle());

        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.home);
                tab.setIcon(R.drawable.ic_round_home_24);
            } else if (position == 1) {
                tab.setText(R.string.profile);
                tab.setIcon(R.drawable.ic_round_perm_identity_24);
            }
        }).attach();
    }
}