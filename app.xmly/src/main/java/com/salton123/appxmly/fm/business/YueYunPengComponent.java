package com.salton123.appxmly.fm.business;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.appxmly.R;
import com.salton123.appxmly.model.ApiMethod;
import com.salton123.appxmly.view.EndLessOnScrollListener;
import com.salton123.appxmly.view.adapter.MrGuoTrackAdapter;
import com.salton123.appxmly.wrapper.XmPlayerStatusAdapter;
import com.salton123.base.BaseSupportFragment;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/19 15:53
 * ModifyTime: 15:53
 * Description:
 */
public class YueYunPengComponent extends BaseSupportFragment {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private MrGuoTrackAdapter mAdapter;
    private int categoryId = 0;
    private int page = 1;
    private int pageSize = 20;
    private String keyword = "郭德纲";
    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_album_list;
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, String value) {
        Bundle bundle = new Bundle();
        bundle.putString("arg_item", value);
        T fragment = null;
        try {
            fragment = clz.newInstance();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        fragment.setArguments(bundle);
        return fragment;
    }

//    Album mAlbum;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        keyword = getArguments().getString(ARG_ITEM);
        mPlayerManager = XmPlayerManager.getInstance(_mActivity);
        mPlayerManager.addPlayerStatusListener(listener);
    }

    @Override
    public void InitViewAndData() {
        refresh = f(R.id.refresh);
        recycler = f(R.id.recycler);
        mAdapter = new MrGuoTrackAdapter(recycler);
        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
            }
        });
        recycler.setAdapter(mAdapter);
        getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                mPlayerManager.playList(mAdapter.getData(), position);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPlayerManager.removePlayerStatusListener(listener);
    }

    XmPlayerStatusAdapter listener = new XmPlayerStatusAdapter() {
        @Override
        public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
            super.onSoundSwitch(playableModel, playableModel1);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    public void getSearchedTracks(String keyword, int categoryId, int calc_dimension, int page, int pageSize) {

        ApiMethod.getSearchedTracks(keyword, calc_dimension, categoryId, page, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SearchTrackList>() {
            @Override
            public void accept(SearchTrackList searchTrackList) throws Exception {
                onSearchedTracksData(searchTrackList);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                onSearchedTracksError(0, throwable.getMessage());
            }
        });
    }


    public void onSearchedTracksData(SearchTrackList searchTrackList) {
        if (searchTrackList.getTracks().size() < pageSize) toast("数据加载完毕");
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
//            page = 1;
            mAdapter.clear();
            mAdapter.setData(searchTrackList.getTracks());
        } else {
            mAdapter.addMoreData(searchTrackList.getTracks());
        }
    }


    public void onSearchedTracksError(int resCode, String errorMsg) {
        toast(errorMsg);
    }
}
