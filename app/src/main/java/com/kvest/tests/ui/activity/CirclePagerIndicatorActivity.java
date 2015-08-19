package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.TestViewPagerAdapter;
import com.kvest.tests.ui.widget.CirclePagerIndicator;

/**
 * Created by Kvest on 03.01.2015.
 */
public class CirclePagerIndicatorActivity extends FragmentActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CirclePagerIndicatorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_pager_indicator_activity);

        initUI();
    }

    private void initUI() {
        ViewPager pager = (ViewPager)findViewById(R.id.test_view_pager);

        TestViewPagerAdapter adapter = new TestViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        final CirclePagerIndicator pageIndicator = (CirclePagerIndicator)findViewById(R.id.test_pager_indicator);
        pageIndicator.setViewPager(pager);
//        pageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("KVEST_TAG", "onPageScrolled[position=" + position + ", positionOffset=" + positionOffset + ", positionOffsetPixels=" + positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                Log.d("KVEST_TAG", "onPageSelected");
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//                Log.d("KVEST_TAG", "onPageScrollStateChanged");
//            }
//        });
    }
}
