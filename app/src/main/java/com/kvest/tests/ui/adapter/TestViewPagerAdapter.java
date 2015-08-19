package com.kvest.tests.ui.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.kvest.tests.ui.fragment.ColorSupportFragment;

/**
 * Created by Kvest on 03.01.2015.
 */
public class TestViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENTS_COUNT = 6;
    private static final int[] FRAGMENTS_COLOR = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.GRAY, Color.CYAN};

    public TestViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ColorSupportFragment.getInstance(FRAGMENTS_COLOR[i]);
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
