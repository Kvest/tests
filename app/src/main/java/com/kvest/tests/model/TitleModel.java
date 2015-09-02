package com.kvest.tests.model;

import com.kvest.tests.R;

/**
 * Created by roman on 9/1/15.
 */
public class TitleModel extends BaseRecyclerViewModel {
    public String title;

    public TitleModel(String title) {
        this.layout = R.layout.title_view;
        this.title = title;
    }
}
