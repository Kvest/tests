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

    private Fragment firstFragment;
    private int count;

    public TestFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        count = FRAGMENTS_COUNT;
    }

    @Override
    public Fragment getItem(int index) {
        if (index == 0 && firstFragment != null) {
            return firstFragment;
        }  else {
            return ColorFragment.getInstance(FRAGMENTS_COLOR[index % FRAGMENTS_COLOR.length]);
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    public void addFragment() {
        ++count;
        notifyDataSetChanged();
    }

    public void setFirstFragment(Fragment firstFragment) {
        this.firstFragment = firstFragment;
        notifyDataSetChanged();
    }
}
