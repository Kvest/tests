package com.kvest.tests.model;

import android.view.View;

import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.ActionsRecyclerViewAdapter;
import com.kvest.tests.ui.holder.ItemHolder;

/**
 * Created by roman on 9/1/15.
 */
public class ItemModel extends BaseRecyclerViewModel {
    public String title;
    public String url;

    public ItemModel(String title, String url) {
        this.layout = R.layout.item_view;
        this.title = title;
        this.url = url;
    }
}
