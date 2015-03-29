package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.kvest.tests.R;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.async_query_handler_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncQueryHandlerActivity.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.recycler_view_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRecyclerViewActivity.startActivity(MainActivity.this);
            }
        });
        findViewById(R.id.circle_pager_indicator_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CirclePagerIndicatorActivity.startActivity(MainActivity.this);
            }
        });
        findViewById(R.id.vnc_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VNCActivity.startActivity(MainActivity.this);
            }
        });
        findViewById(R.id.fragments_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentsTestActivity.startActivity(MainActivity.this);
            }
        });
    }
}
