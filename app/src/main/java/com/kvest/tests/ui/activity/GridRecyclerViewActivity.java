package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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

        adapter = new UniversalRecyclerViewAdapter(generateDataset());

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new SlideInUpAnimator());

        //set an adapter
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                                                   ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();

                        return adapter.onItemMove(fromPos, toPos);
                    }
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        adapter.onItemDismiss(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private BaseRecyclerViewModel[] generateDataset() {
        BaseRecyclerViewModel[] result = new BaseRecyclerViewModel[NEW_ITEM_IMAGE_URLS.length + ITEM_IMAGE_URLS.length + 2];
        int index = 0;
        result[index++] = new TitleModel("First images list:");
        result[index - 1].setSpanSize(3);
        for (String url : NEW_ITEM_IMAGE_URLS) {
            result[index++] = new ImageItemModel(url);
            if (index >= 3 && index <= 5) {
                result[index - 1].setSpanSize(2);
            } else {
                result[index - 1].setSpanSize(1);
            }
        }
        result[index++] = new TitleModel("Second images list:");
        result[index - 1].setSpanSize(3);
        for (String url : ITEM_IMAGE_URLS) {
            result[index++] = new ImageItemModel(url);
            result[index - 1].setSpanSize(1);
        }

        return result;
    }
}
