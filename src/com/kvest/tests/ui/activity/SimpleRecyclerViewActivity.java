package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.kvest.tests.R;

import android.support.v7.widget.RecyclerView;
import com.kvest.tests.ui.adapter.SimpleRecyclerViewAdapter;
import com.kvest.tests.ui.widget.CustomLayoutManager;

/**
 * User: roman
 * Date: 12/26/14
 * Time: 1:31 PM
 */
public class SimpleRecyclerViewActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private static final int DATASET_SIZE = 1000;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SimpleRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    //TODO
    // 3) Кастомный LayoutManager
    // 4) Анимации
    // 5) Разные адаптеры

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_recycler_view_activity);

        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RadioGroup layoutManagerType = (RadioGroup)findViewById(R.id.layout_manager_type);
        layoutManagerType.setOnCheckedChangeListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //set layout manager
        onCheckedChanged(layoutManagerType, layoutManagerType.getCheckedRadioButtonId());

        //create dataset
        String[] dataset = new String[DATASET_SIZE];
        for (int i = 0; i < DATASET_SIZE; ++i) {
            dataset[i] = "Test " + i;
        }

        // specify an adapter (see also next example)
        mAdapter = new SimpleRecyclerViewAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.vertical_layout_manager :
                // use vertical linear layout manager
                RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(verticalLayoutManager);

                break;
            case R.id.horizontal_layout_manager :
                // use vertical linear layout manager
                RecyclerView.LayoutManager horizontallLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(horizontallLayoutManager);
                break;
            case R.id.grid_layout_manager :
                RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 3 , GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(gridLayoutManager);
                break;
            case R.id.staggered_grid_layout_manager :
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
            case R.id.custom_layout_manager :
                RecyclerView.LayoutManager customLayoutManager = new CustomLayoutManager();
                mRecyclerView.setLayoutManager(customLayoutManager);
                break;
        }
    }
}
