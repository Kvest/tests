package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kvest.tests.R;

/**
 * Created by Kvest on 25.03.2015.
 */
public class FragmentsTestActivity extends Activity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FragmentsTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_test_activity);

        findViewById(R.id.static_fragment_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticFragmentActivity.startActivity(FragmentsTestActivity.this);
            }
        });

        findViewById(R.id.dynamic_fragment_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicFragmentActivity.startActivity(FragmentsTestActivity.this);
            }
        });

        findViewById(R.id.viewpager_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestFragmentPagerAdapterActivity.startActivity(FragmentsTestActivity.this);
            }
        });
    }
}
