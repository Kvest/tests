package com.kvest.tests.ui.widget;

import android.support.v7.widget.RecyclerView;

/**
 * User: roman
 * Date: 1/21/15
 * Time: 11:26 AM
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
//
//    @Override
//    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        !
//    }
}
