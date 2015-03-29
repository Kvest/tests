package com.kvest.tests.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import com.kvest.tests.ui.fragment.ColorFragment;

/**
 * Created by Kvest on 29.03.2015.
 */
public class TestFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENTS_COUNT = 3;
    private static final int[] FRAGMENTS_COLOR = new int[]{Color.BLUE, Color.RED, Color.GREEN};

    public TestFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        return ColorFragment.getInstance(FRAGMENTS_COLOR[index]);
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
