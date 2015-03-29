package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.kvest.tests.R;

/**
 * Created by Kvest on 25.03.2015.
 */
public class StaticFragmentActivity extends FragmentActivity {
    private static final String TAG = "StaticFragmentActivity";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StaticFragmentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_fragment_activity);

        Log.d(TAG, "onCreate(savedInstanceState == null - " +(savedInstanceState == null) + ")");
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
