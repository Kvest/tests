package com.kvest.tests.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.RadioGroup;

import com.kvest.tests.R;

/**
 * Created by kvest on 21.08.15.
 */
public class FabAndSnackbarActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private RadioGroup fabActions;
    private View actionsPanel;
    private MoveFABToRevealPositionListener moveFABToRevealPositionListener;
    private MoveFABFromRevealPositionListener moveFABFromRevealPositionListener;

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
        moveFABToRevealPositionListener = new MoveFABToRevealPositionListener();
        moveFABFromRevealPositionListener = new MoveFABFromRevealPositionListener();

        actionsPanel = findViewById(R.id.actions_panel);
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
        findViewById(R.id.close_panel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfromPanelToFab();
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
                .setListener(null)
                .start();
        } else {
            fab.animate()
               .rotation(225f)
               .setDuration(500)
               .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(null)
               .start();
        }
    }

    private void transformFAB() {
        //calculate the FAB's reveal position
        int cx = actionsPanel.getLeft() + 2 * (actionsPanel.getWidth() / 3);
        int cy = (actionsPanel.getTop() + actionsPanel.getBottom()) / 2;

        //move FAB to the reveal position
        fab.animate()
            .translationX(cx - (fab.getLeft() + fab.getRight()) / 2)
            .translationY(cy - (fab.getTop() + fab.getBottom()) / 2)
            .setDuration(250)
            .setInterpolator(new OvershootInterpolator(3f))
            .setListener(moveFABToRevealPositionListener)
            .start();
    }

    private void transfromPanelToFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = 2 * (actionsPanel.getWidth() / 3);
            int cy = actionsPanel.getHeight() / 2;
            float radius = Math.max(actionsPanel.getWidth(), actionsPanel.getHeight()) * 1.5f;
            int fabRadius = (fab.getRight() - fab.getLeft()) / 2;
            Animator reveal = ViewAnimationUtils.createCircularReveal(actionsPanel, cx, cy, radius, fabRadius);
            reveal.setDuration(500);
            reveal.addListener(moveFABFromRevealPositionListener);
            reveal.start();
        } else {
            moveFABFromRevealPosition();
        }
    }

    public void moveFABFromRevealPosition() {
        actionsPanel.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);

        fab.animate()
            .translationX(0)
            .translationY(0)
            .setDuration(250)
            .setInterpolator(new LinearOutSlowInInterpolator())
            .setListener(null)
            .start();
    }

    private class MoveFABToRevealPositionListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationEnd(Animator animation) {
            fab.setVisibility(View.INVISIBLE);
            actionsPanel.setVisibility(View.VISIBLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                float radius = Math.max(actionsPanel.getWidth(), actionsPanel.getHeight()) * 1.5f;
                int fabRadius = (fab.getRight() - fab.getLeft()) / 2;
                int cx = 2 * (actionsPanel.getWidth() / 3);
                int cy = actionsPanel.getHeight() / 2;

                ViewAnimationUtils.createCircularReveal(actionsPanel, cx, cy, fabRadius, radius).setDuration(500).start();
            }
        }
    }

    private class MoveFABFromRevealPositionListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationEnd(Animator animation) {
            moveFABFromRevealPosition();
        }
    }
}
