package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.TestFragmentPagerAdapter;
import com.kvest.tests.ui.fragment.ColorFragment;

/**
 * Created by Kvest on 29.03.2015.
 */
public class TestFragmentPagerAdapterActivity extends Activity {
    private TestFragmentPagerAdapter adapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestFragmentPagerAdapterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment_pager_adapter_activity);

        initUI();
    }

    private void initUI() {
        final ViewPager pager = (ViewPager)findViewById(R.id.test_view_pager);

        adapter = new TestFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);

        findViewById(R.id.change_first_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setFirstFragment(ColorFragment.getInstance(Color.YELLOW));
            }
        });

        findViewById(R.id.notify_dataset_changed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addFragment();
            }
        });
    }
}
