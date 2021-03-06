package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.tab_layout_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayoutTestActivity.start(MaterialTestsActivity.this);
            }
        });
        findViewById(R.id.fab_and_snackbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabAndSnackbarActivity.start(MaterialTestsActivity.this);
            }
        });
        findViewById(R.id.app_bar_layout_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBarLayoutTestActivity.start(MaterialTestsActivity.this);
            }
        });
        findViewById(R.id.collapsing_tool_layout_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CollapsingToolLayoutTestActivity.start(MaterialTestsActivity.this);
            }
        });
        findViewById(R.id.collapsing_tool_layout_test_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollapsingToolLayoutTestActivityWithFragment.start(MaterialTestsActivity.this);
            }
        });

        findViewById(R.id.edit_text_email).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.edit_text_email_wrapper);
                    if (TextUtils.isEmpty(((EditText) view).getText())) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError("Email can't be null");
                    } else {
                        textInputLayout.setError(null);
                        textInputLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        //cardview toolbar
        Toolbar cardviewToolbar = (Toolbar) findViewById(R.id.cardview_toolbar);
        cardviewToolbar.setTitle("CardView title");
        cardviewToolbar.inflateMenu(R.menu.cardview_menu);
        cardviewToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MaterialTestsActivity.this, "CardView toolbar action", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
