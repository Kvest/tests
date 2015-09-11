package com.kvest.tests.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kvest.tests.R;
import com.kvest.tests.model.BaseRecyclerViewModel;
import com.kvest.tests.model.ImageItemModel;
import com.kvest.tests.model.ItemModel;
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
public class UniversalRecyclerViewAdapter extends RecyclerView.Adapter<UniversalRecyclerViewAdapter.ViewHolder> implements OnItemSelectedListener {
    private List<BaseRecyclerViewModel> dataset;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    private OnImageSelectedListener onImageSelectedListener;

    public UniversalRecyclerViewAdapter(BaseRecyclerViewModel[] dataset) {
        this.dataset = new ArrayList<>(Arrays.asList(dataset));
    }

    @Override
    public UniversalRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        ViewHolder holder = null;

        switch (viewType) {
            case R.layout.title_view :
                holder = new TitleHolder(v);
                break;
            case R.layout.item_view :
                holder = new ItemHolder(v);
                holder.setOnItemSelectedListener(this);
                break;
            case R.layout.image_item_view :
                holder = new ImageItemHolder(v);
                holder.setOnItemSelectedListener(this);
                break;
        }

        return holder;
    }

    @Override
    public void onItemSelected(View view, int position, long id) {
        if (onImageSelectedListener != null) {
            BaseRecyclerViewModel model = dataset.get(position);
            //DON'T DO THIS WAY. THIS IS AN UGLY SOLUTION AND ONLY FOR TEST PURPOSES
            if (model instanceof ItemModel) {
                onImageSelectedListener.onImageSelected((ImageView)view.findViewById(R.id.image), ((ItemModel) model).url);
            } else if (model instanceof ImageItemModel) {
                onImageSelectedListener.onImageSelected((ImageView)view.findViewById(R.id.image), ((ImageItemModel) model).url);
            }
        }
    }

    public OnImageSelectedListener getOnImageSelectedListener() {
        return onImageSelectedListener;
    }

    public void setOnImageSelectedListener(OnImageSelectedListener onImageSelectedListener) {
        this.onImageSelectedListener = onImageSelectedListener;
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

    public void onItemDismiss(int position) {
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataset, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataset, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void add(int index, BaseRecyclerViewModel item) {
        dataset.add(index, item);
        notifyItemInserted(index);
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).layout;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnItemSelectedListener onItemSelectedListener;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
            this.onItemSelectedListener = onItemSelectedListener;
        }


        @Override
        public void onClick(View view) {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(view, getAdapterPosition(), getItemId());
            }
        }

        public abstract void onBindView(BaseRecyclerViewModel model);
    }

    //not a good idea to do it this way. This is just for the purposes of learning
    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        if (spanSizeLookup == null) {
            spanSizeLookup = new SpanSizeLookup();
        }
        return spanSizeLookup;
    }

    private class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return dataset.get(position).getSpanSize();
        }
    }
}
