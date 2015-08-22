package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.kvest.tests.R;

/**
 * Created by kvest on 21.08.15.
 */
public class FabAndSnackbarActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private RadioGroup fabActions;

    public static void start(Context context) {
        Intent intent = new Intent(context, FabAndSnackbarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_and_snackbar_activity);

        init();
    }

    private void init() {
        fabActions = (RadioGroup)findViewById(R.id.fab_actions);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fabActions.getCheckedRadioButtonId()) {
                    case R.id.action_show_snackbar :
                        showSnackbar();
                        break;
                    case R.id.action_show_subitems:
                        showSubitems();
                        break;
                    case R.id.action_transform:
                        transformFAB();
                        break;
                }
            }
        });
    }

    private void showSnackbar() {
        Snackbar.make(coordinatorLayout, "FAB action", Snackbar.LENGTH_SHORT).show();
    }

    private void showSubitems() {
        //TODO add subitems with a fog
        if (fab.getRotation() > 0) {
            fab.animate()
                .rotation(0f)
                .setDuration(500)
                .setInterpolator(new LinearOutSlowInInterpolator())
                .start();
        } else {
            fab.animate()
               .rotation(225f)
               .setDuration(500)
               .setInterpolator(new LinearOutSlowInInterpolator())
               .start();
        }
    }

    private void transformFAB() {
        //TODO
    }
}
