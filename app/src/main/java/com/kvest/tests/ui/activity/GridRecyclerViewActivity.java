package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ImageItemModel;
import com.kvest.tests.model.TitleModel;
import com.kvest.tests.ui.adapter.UniversalRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by roman on 9/1/15.
 */
public class GridRecyclerViewActivity extends Activity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GridRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    private static final String[] NEW_ITEM_IMAGE_URLS = {
            "http://rdiodynimages0-a.akamaihd.net/?l=a5791435-1",
            "http://rdiodynimages0-a.akamaihd.net/?l=a5776410-1",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5732739-4",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5702830-2",
            "http://rdiodynimages0-a.akamaihd.net/?l=a5722293-1",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5753865-2",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5809025-3",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5772148-1",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5758415-1",
            "http://rdiodynimages3-a.akamaihd.net/?l=a5743329-1",
    };


    private static final String[] ITEM_IMAGE_URLS = {
            "http://rdiodynimages0-a.akamaihd.net/?l=a5791435-1",
            "http://rdiodynimages0-a.akamaihd.net/?l=a5776410-1",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5732739-4",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5702830-2",
            "http://rdiodynimages0-a.akamaihd.net/?l=a5722293-1",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5753865-2",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5809025-3",
            "http://rdiodynimages2-a.akamaihd.net/?l=a5772148-1",
            "http://rdiodynimages1-a.akamaihd.net/?l=a5758415-1",
            "http://rdiodynimages3-a.akamaihd.net/?l=a5743329-1",
    };

    private UniversalRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private int[] spanSizes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        init();
    }

    private void init() {
        //configure UIL
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .resetViewBeforeLoading(true)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return spanSizes[position];
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new SlideInUpAnimator());

        //set an adapter
        adapter = new UniversalRecyclerViewAdapter(generateDataset());
        recyclerView.setAdapter(adapter);
    }

    private BaseRecyclerViewModel[] generateDataset() {
        BaseRecyclerViewModel[] result = new BaseRecyclerViewModel[NEW_ITEM_IMAGE_URLS.length + ITEM_IMAGE_URLS.length + 2];
        spanSizes = new int[result.length];
        int index = 0;
        result[index++] = new TitleModel("First images list:");
        spanSizes[index - 1] = 3;
        for (String url : NEW_ITEM_IMAGE_URLS) {
            result[index++] = new ImageItemModel(url);
            if (index >= 3 && index <= 5) {
                spanSizes[index - 1] = 2;
            } else {
                spanSizes[index - 1] = 1;
            }
        }
        result[index++] = new TitleModel("Second images list:");
        spanSizes[index - 1] = 3;
        for (String url : ITEM_IMAGE_URLS) {
            result[index++] = new ImageItemModel(url);
            spanSizes[index - 1] = 1;
        }

        return result;
    }
}
