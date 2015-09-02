package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by roman on 9/1/15.
 */
public class GridRecyclerViewActivity extends Activity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GridRecyclerViewActivity.class);
        context.startActivity(intent);
    }
}
