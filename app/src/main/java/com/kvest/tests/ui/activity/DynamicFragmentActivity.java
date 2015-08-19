package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.kvest.tests.R;
import com.kvest.tests.ui.fragment.ColorSupportFragment;

/**
 * Created by Kvest on 25.03.2015.
 */
public class DynamicFragmentActivity extends FragmentActivity {
    private static final String TAG = "DynamicFragmentActivity";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DynamicFragmentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_fragment_activity);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, ColorSupportFragment.getInstance(Color.GREEN));
            transaction.commit();
        }

        Log.d(TAG, "onCreate(savedInstanceState == null - " + (savedInstanceState == null) + ")");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }
}
