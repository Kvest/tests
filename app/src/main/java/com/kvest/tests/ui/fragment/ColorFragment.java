package com.kvest.tests.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kvest on 29.03.2015.
 */
public class ColorFragment extends Fragment {
    private static final String TAG = "ColorFragment";
    private static final String ARGUMENT_COLOR = "com.kvest.tests.argument.COLOR";

    public static Fragment getInstance(int color) {
        Bundle arguments = new Bundle(1);
        arguments.putInt(ARGUMENT_COLOR, color);

        ColorFragment fragment = new ColorFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(savedInstanceState == null - " + (savedInstanceState == null) + ")");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = new View(getActivity());
        view.setBackgroundColor(getColor());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "onDetach");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated(Bundle == null - " +(savedInstanceState == null) + ")");
    }

    private int getColor() {
        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(ARGUMENT_COLOR)) {
            return arguments.getInt(ARGUMENT_COLOR);
        } else {
            return Color.BLACK;
        }
    }
}
