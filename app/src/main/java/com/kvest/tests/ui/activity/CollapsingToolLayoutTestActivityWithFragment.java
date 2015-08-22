package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kvest.tests.R;
import com.kvest.tests.ui.fragment.CollapsingToolLayoutTestFragment;

/**
 * Created by kvest on 22.08.15.
 */
public class CollapsingToolLayoutTestActivityWithFragment extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, CollapsingToolLayoutTestActivityWithFragment.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container_layout);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, CollapsingToolLayoutTestFragment.newInstance());
            transaction.commit();
        }
    }
}
