package com.salton123.sf.appflow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.salton123.sf.appflow.ui.fragment.discover.PagerChildFragment;


/**
 * Created by YoKeyword on 16/2/5.
 */
public class DiscoverFragmentAdapter extends FragmentPagerAdapter {
    String[] mTitles;

    public DiscoverFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PagerChildFragment.newInstance(0);
        } else if (position == 1) {
            return PagerChildFragment.newInstance(1);
        } else {
            return PagerChildFragment.newInstance(2);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
