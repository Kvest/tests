package com.kvest.tests.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.kvest.tests.R;
import com.kvest.tests.model.ItemModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by roman on 9/11/15.
 */
public class ImageDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_IMAGE_URL = "com.kvest.tests.extras.IMAGE_URL";

    public static void startActivity(AppCompatActivity context, View imageView, String imageUrl) {


        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, imageView, "image");
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details_activity);

        String imageUrl = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_IMAGE_URL)) {
            imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        }

        init(imageUrl);
    }

    private void init(String imageUrl) {
        ((TextView) findViewById(R.id.url)).setText(imageUrl);

        if (imageUrl != null) {
            ImageView image = (ImageView) findViewById(R.id.image);
            Picasso.with(this).load(imageUrl).into(image);
        }
    }

}
