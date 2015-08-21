package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kvest.tests.R;

/**
 * Created by kvest on 21.08.15.
 */
public class MaterialTestsActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, MaterialTestsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_tests_activity);

        findViewById(R.id.tab_layout_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayoutTestActivity.start(MaterialTestsActivity.this);
            }
        });
    }
}
