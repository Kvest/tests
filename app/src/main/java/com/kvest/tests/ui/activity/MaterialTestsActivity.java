package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
    }
}
