package com.kvest.tests.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kvest.tests.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 5/27/16.
 */
public class AnimationsRecyclerViewActivity extends AppCompatActivity {
    private List<Data> INITIAL_DATA = new ArrayList<Data>(){{add(new Data(1, "Data 1")); add(new Data(2, "Data 2"));
        add(new Data(3, "Data 3")); add(new Data(4, "Data 4"));}};

    private RecyclerView recyclerView;
    private SimpleRecyclerViewAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText idAdd;
    private EditText nameAdd;
    private EditText positionAdd;
    private EditText idDelete;
    private EditText idEdit;
    private EditText newNameEdit;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnimationsRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recycler_view_animations);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //set adapter
        adapter = new SimpleRecyclerViewAdapter(INITIAL_DATA);
        recyclerView.setAdapter(adapter);

        //set bottom sheet
        View bottomSheet = findViewById( R.id.edit_panel);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        //setup fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditPanel();
            }
        });

        //edit panel
        idAdd = (EditText)findViewById(R.id.item_id);
        nameAdd = (EditText)findViewById(R.id.item_name);
        positionAdd = (EditText)findViewById(R.id.item_position);
        idDelete = (EditText)findViewById(R.id.item_id_del);
        idEdit = (EditText)findViewById(R.id.item_id_edit);
        newNameEdit = (EditText)findViewById(R.id.item_new_name);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
            }
        });
    }

    private void addNewItem() {
        if (TextUtils.isEmpty(idAdd.getText())) {
            return;
        }

        long id = Long.parseLong(idAdd.getText().toString());
        String name = nameAdd.getText().toString();

        if (TextUtils.isEmpty(positionAdd.getText())) {
            adapter.add(new Data(id, name));
        } else {
            int position = Integer.parseInt(positionAdd.getText().toString());
            adapter.insert(new Data(id, name), position);
        }
    }

    private void deleteItem() {
        if (TextUtils.isEmpty(idDelete.getText())) {
            return;
        }

        long id = Long.parseLong(idDelete.getText().toString());
        adapter.remove(id);
    }

    private void editItem() {
        if (TextUtils.isEmpty(idEdit.getText())) {
            return;
        }

        long id = Long.parseLong(idEdit.getText().toString());
        String newName = newNameEdit.getText().toString();
        adapter.updateName(id, newName);
    }

    private void showEditPanel() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static class Data {
        public long id;
        public String name;

        public Data(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {
        private List<Data> dataset;

        public SimpleRecyclerViewAdapter(List<Data> dataset) {
            this.dataset = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_item, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder vh, int position) {
            Data data = dataset.get(position);
            vh.id.setText(Long.toString(data.id));
            vh.name.setText(data.name);
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView id;
            public TextView name;

            public ViewHolder(View itemView) {
                super(itemView);

                id = (TextView) itemView.findViewById(R.id.type);
                name = (TextView) itemView.findViewById(R.id.name);
            }
        }

        public void add(Data data) {
            dataset.add(data);
            notifyItemInserted(dataset.size() - 1);
        }

        public void insert(Data data, int position) {
            dataset.add(position, data);
            notifyItemInserted(position);
        }

        public void remove(long id) {
            for (int i = dataset.size() - 1; i >= 0; --i) {
                if (dataset.get(i).id == id) {
                    dataset.remove(i);
                    notifyItemRemoved(i);
                }
            }
        }

        public void updateName(long id, String newName) {
            for (int i = 0; i < dataset.size(); i++) {
                if (dataset.get(i).id == id) {
                    dataset.get(i).name = newName;
                    notifyItemChanged(i);
                }
            }
        }
    }
}
