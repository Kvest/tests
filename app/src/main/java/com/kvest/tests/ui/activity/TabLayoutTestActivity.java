package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.TestViewPagerAdapter;

/**
 * Created by kvest on 21.08.15.
 */
public class TabLayoutTestActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, TabLayoutTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_test_activity);

        init();
    }

    private void init() {
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        ((RadioGroup)findViewById(R.id.tabs_gravity)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gravity_fill :
                        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                        break;
                    case R.id.gravity_centre :
                        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                        break;
                }
            }
        });

        ((RadioGroup)findViewById(R.id.tabs_mode)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mode_fixed :
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        break;
                    case R.id.mode_scrollable :
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        break;
                }
            }
        });
    }
}
