package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kvest.tests.R;

/**
 * User: roman
 * Date: 1/20/15
 * Time: 10:14 AM
 */
public class TestRecyclerViewActivity extends Activity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recycler_view_activity);

        init();
    }

    private void init() {
        findViewById(R.id.simple_recycler_view_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleRecyclerViewActivity.startActivity(TestRecyclerViewActivity.this);
            }
        });
    }
}
