package com.kvest.tests.model;

import android.view.View;

import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.ActionsRecyclerViewAdapter;
import com.kvest.tests.ui.holder.TitleHolder;

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
