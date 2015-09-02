package com.kvest.tests.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.ui.holder.ItemHolder;
import com.kvest.tests.ui.holder.TitleHolder;

/**
 * Created by roman on 9/1/15.
 */
public class ActionsRecyclerViewAdapter extends RecyclerView.Adapter<ActionsRecyclerViewAdapter.ViewHolder> {
    private BaseRecyclerViewModel[] dataset;

    public ActionsRecyclerViewAdapter(BaseRecyclerViewModel[] dataset) {
        this.dataset = dataset;
    }

    @Override
    public ActionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.title_view :
                return new TitleHolder(v);
            case R.layout.item_view :
                return new ItemHolder(v);
        }

        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ActionsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.onBindView(dataset[position]);
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public int getItemViewType(int position) {
        return dataset[position].layout;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBindView(BaseRecyclerViewModel model);
    }
}
