package com.kvest.tests.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ItemModel;
import com.kvest.tests.ui.adapter.ActionsRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by roman on 9/1/15.
 */
public class ItemHolder extends ActionsRecyclerViewAdapter.ViewHolder {
    private TextView title;
    private ImageView image;

    public ItemHolder(View view) {
        super(view);

        title = (TextView) view.findViewById(R.id.title);
        image = (ImageView) view.findViewById(R.id.image);
    }

    @Override
    public void onBindView(BaseRecyclerViewModel model) {
        title.setText(((ItemModel) model).title);
        ImageLoader.getInstance().displayImage(((ItemModel) model).url, image);
    }
}
