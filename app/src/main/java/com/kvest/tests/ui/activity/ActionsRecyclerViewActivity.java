package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.RadioGroup;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ItemModel;
import com.kvest.tests.model.TitleModel;
import com.kvest.tests.ui.adapter.UniversalRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by roman on 9/1/15.
 */
public class ActionsRecyclerViewActivity extends Activity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActionsRecyclerViewActivity.class);
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

    private RecyclerView recyclerView;
    private UniversalRecyclerViewAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions_recycler_view_activity);

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //set an adapter
        adapter = new UniversalRecyclerViewAdapter(generateDataset());
        recyclerView.setAdapter(adapter);

        RadioGroup callbackType = (RadioGroup) findViewById(R.id.callback_type);
        callbackType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.simple_callback :
                        setSimpleCallback();
                        break;
                    case R.id.custom_callback :
                        setCustomCallback();
                        break;
                }
            }
        });
        callbackType.check(R.id.custom_callback);
    }

    private void setSimpleCallback() {
        //detach
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView(null);
        }

        itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
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

    private void setCustomCallback() {
        //detach
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView(null);
        }

        itemTouchHelper = new ItemTouchHelper( new CustomItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private BaseRecyclerViewModel[] generateDataset() {
        BaseRecyclerViewModel[] result = new BaseRecyclerViewModel[NEW_ITEM_IMAGE_URLS.length + ITEM_IMAGE_URLS.length + 2];
        int index = 0;
        result[index++] = new TitleModel("First images list:");
        for (String url : NEW_ITEM_IMAGE_URLS) {
            result[index++] = new ItemModel("Image from " + url, url);
        }
        result[index++] = new TitleModel("Second images list:");
        for (String url : ITEM_IMAGE_URLS) {
            result[index++] = new ItemModel("Url: " + url, url);
        }

        return result;
    }

    public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            adapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

//        @Override
//        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
//                itemViewHolder.onItemSelected();
//            }
//
//            super.onSelectedChanged(viewHolder, actionState);
//        }
//
//        @Override
//        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            super.clearView(recyclerView, viewHolder);
//
//            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
//            itemViewHolder.onItemClear();
//        }
    }
}
