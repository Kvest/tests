package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kvest.tests.R;

/**
 * Created by roman on 5/27/16.
 */
public class AnimationsRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnimationsRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recycler_view_aanimations);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //set adapter
        //TODO
    }
}
