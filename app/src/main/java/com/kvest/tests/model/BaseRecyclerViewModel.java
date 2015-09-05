package com.kvest.tests.model;

/**
 * Created by roman on 9/1/15.
 */
public abstract class BaseRecyclerViewModel {
    private int spanSize = 1;
    public int layout;

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }
}
