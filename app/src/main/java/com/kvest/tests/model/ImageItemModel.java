package com.kvest.tests.model;

import com.kvest.tests.R;

/**
 * Created by roman on 9/2/15.
 */
public class ImageItemModel extends BaseRecyclerViewModel {
    public String url;

    public ImageItemModel(String url) {
        this.layout = R.layout.image_item_view;
        this.url = url;
    }
}
