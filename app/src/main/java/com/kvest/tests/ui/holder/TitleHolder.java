package com.kvest.tests.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ItemModel;
import com.kvest.tests.model.TitleModel;
import com.kvest.tests.ui.adapter.ActionsRecyclerViewAdapter;

import org.w3c.dom.Text;

/**
 * Created by roman on 9/1/15.
 */
public class TitleHolder extends ActionsRecyclerViewAdapter.ViewHolder {
    private TextView title;

    public TitleHolder(View view) {
        super(view);

        title = (TextView)view;
    }

    @Override
    public void onBindView(BaseRecyclerViewModel model) {
        title.setText(((TitleModel) model).title);
    }
}
