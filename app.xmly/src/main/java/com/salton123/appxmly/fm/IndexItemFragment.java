package com.salton123.appxmly.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.salton123.appxmly.R;
import com.salton123.base.BaseSupportFragment;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexItemFragment extends BaseSupportFragment{

    private RecyclerView recycler;
    @Override
    public int GetLayout() {
        return R.layout.fm_index_item;
    }

    String keyword ;
    @Override
    public void InitVariable(Bundle savedInstanceState) {
        keyword  = getArguments().getString(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        recycler = f(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(_mActivity,2));
//        mAdapter = new IndexItemAdapter(R.layout.item_section_content,R.layout.def_section_head,mData);
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
