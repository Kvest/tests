package com.kvest.tests.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kvest on 03.01.2015.
 */
public class ColorFragment extends Fragment {
    private static final String ARGUMENT_COLOR = "com.kvest.tests.argument.COLOR";

    public static Fragment getInstance(int color) {
        Bundle arguments = new Bundle(1);
        arguments.putInt(ARGUMENT_COLOR, color);

        ColorFragment fragment = new ColorFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = new View(getActivity());
        view.setBackgroundColor(getColor());
        return view;
    }

    private int getColor() {
        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(ARGUMENT_COLOR)) {
            return arguments.getInt(ARGUMENT_COLOR);
        } else {
            return Color.WHITE;
        }
    }
}
