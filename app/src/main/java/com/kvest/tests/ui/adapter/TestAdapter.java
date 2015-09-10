package com.kvest.tests.ui.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kvest.tests.R;

import static com.kvest.tests.provider.TestProviderContract.Tables.*;

/**
 * User: roman
 * Date: 12/8/14
 * Time: 11:30 AM
 */
public class TestAdapter extends CursorAdapter {
    public static final String[] PROJECTION = new String[]{Tests.Columns._ID, Tests.Columns.TYPE, Tests.Columns.NAME};

    private int typeColumnIndex = -1;
    private int nameColumnIndex = -1;

    public TestAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Log.d("KVEST_TAG", "newView");
        //create view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test_list_item, viewGroup, false);


        //create holder
        ViewHolder holder = new ViewHolder();
        holder.type = (TextView) view.findViewById(R.id.type);
        holder.name = (TextView) view.findViewById(R.id.name);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

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
        typeColumnIndex = cursor.getColumnIndex(Tests.Columns.TYPE);
        nameColumnIndex = cursor.getColumnIndex(Tests.Columns.NAME);
    }

    private static class ViewHolder {
        public TextView type;
        public TextView name;
    }
}
