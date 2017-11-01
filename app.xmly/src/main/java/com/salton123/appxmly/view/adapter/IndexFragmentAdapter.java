package com.salton123.appxmly.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/14 15:42
 * ModifyTime: 15:42
 * Description:
 */
public class IndexFragmentAdapter extends FragmentPagerAdapter {
    private List<Pair<Fragment, String>> mData = new ArrayList<>();

    public IndexFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Pair<Fragment, String>> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void add(int position , Pair<Fragment,String> item){
        mData.add(position,item);
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position).first;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).second;
    }
}
