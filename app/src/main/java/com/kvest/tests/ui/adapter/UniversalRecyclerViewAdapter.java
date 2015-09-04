package com.kvest.tests.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.ui.holder.ImageItemHolder;
import com.kvest.tests.ui.holder.ItemHolder;
import com.kvest.tests.ui.holder.TitleHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by roman on 9/1/15.
 */
public class UniversalRecyclerViewAdapter extends RecyclerView.Adapter<UniversalRecyclerViewAdapter.ViewHolder> {
    private List<BaseRecyclerViewModel> dataset;

    public UniversalRecyclerViewAdapter(BaseRecyclerViewModel[] dataset) {
        this.dataset = new ArrayList<>(Arrays.asList(dataset));
    }

    @Override
    public UniversalRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.title_view :
                return new TitleHolder(v);
            case R.layout.item_view :
                return new ItemHolder(v);
            case R.layout.image_item_view :
                return new ImageItemHolder(v);
        }

        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UniversalRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.onBindView(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(int index, BaseRecyclerViewModel item) {
        dataset.add(index, item);
        notifyItemInserted(index);
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).layout;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBindView(BaseRecyclerViewModel model);
    }
}
