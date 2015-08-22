package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.SimpleRecyclerViewAdapter;

/**
 * Created by kvest on 22.08.15.
 */
public class AppBarLayoutTestActivity extends AppCompatActivity {
    private static final int DATASET_SIZE = 100;

    private CoordinatorLayout coordinatorLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, AppBarLayoutTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_layout_test_activity);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_test) {
            Snackbar.make(coordinatorLayout, "Toolbar action", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Tab One"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Two"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Three"));

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //create dataset
        String[] dataset = new String[DATASET_SIZE];
        for (int i = 0; i < DATASET_SIZE; ++i) {
            dataset[i] = "Test " + i;
        }

        // specify an adapter (see also next example)
        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter(dataset);
        recyclerView.setAdapter(adapter);
    }
}
