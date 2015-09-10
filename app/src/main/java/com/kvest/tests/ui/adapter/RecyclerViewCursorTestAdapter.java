package com.kvest.tests.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.tests.R;
import com.kvest.tests.provider.TestProviderContract;

/**
 * Created by roman on 9/10/15.
 */
public class RecyclerViewCursorTestAdapter extends CursorRecyclerAdapter<RecyclerViewCursorTestAdapter.ViewHolder> {
    public static final String[] PROJECTION = new String[]{TestProviderContract.Tables.Tests.Columns._ID, TestProviderContract.Tables.Tests.Columns.TYPE, TestProviderContract.Tables.Tests.Columns.NAME};

    private int typeColumnIndex = -1;
    private int nameColumnIndex = -1;

    public RecyclerViewCursorTestAdapter(Context context){
        super(context,null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("KVEST_TAG", "onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolderCursor(ViewHolder holder, Cursor cursor) {
        if (!isColumnIndexesCalculated()) {
            calculateColumnIndexes(cursor);
        }

        holder.type.setText(Integer.toString(cursor.getInt(typeColumnIndex)));
        holder.name.setText(cursor.getString(nameColumnIndex));
    }

    private boolean isColumnIndexesCalculated() {
        return (typeColumnIndex >= 0);
    }


    private void calculateColumnIndexes(Cursor cursor) {
        typeColumnIndex = cursor.getColumnIndex(TestProviderContract.Tables.Tests.Columns.TYPE);
        nameColumnIndex = cursor.getColumnIndex(TestProviderContract.Tables.Tests.Columns.NAME);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type;
        public TextView name;

        public ViewHolder(View view) {
            super(view);

            type = (TextView) view.findViewById(R.id.type);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
