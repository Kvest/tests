package com.kvest.tests.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ImageItemModel;
import com.kvest.tests.ui.adapter.UniversalRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by roman on 9/2/15.
 */
public class ImageItemHolder extends UniversalRecyclerViewAdapter.ViewHolder {
    private ImageView image;

    public ImageItemHolder(View view) {
        super(view);

        image = (ImageView) view;
    }

    @Override
    public void onBindView(BaseRecyclerViewModel model) {
        ImageLoader.getInstance().displayImage(((ImageItemModel) model).url, image);
    }
}
