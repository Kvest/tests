package com.kvest.tests.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.SimpleRecyclerViewAdapter;

/**
 * Created by kvest on 22.08.15.
 */
public class CollapsingToolLayoutTestFragment extends Fragment {
    public static CollapsingToolLayoutTestFragment newInstance() {
        return new CollapsingToolLayoutTestFragment();
    }

    private static final int DATASET_SIZE = 100;

    private CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.collapsing_tool_layout_test_activity, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View rootView = getView();
        coordinatorLayout = (CoordinatorLayout)rootView.findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //create dataset
        String[] dataset = new String[DATASET_SIZE];
        for (int i = 0; i < DATASET_SIZE; ++i) {
            dataset[i] = "Test " + i;
        }

        // specify an adapter (see also next example)
        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter(dataset);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.test_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_test) {
            Snackbar.make(coordinatorLayout, "Toolbar action", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
