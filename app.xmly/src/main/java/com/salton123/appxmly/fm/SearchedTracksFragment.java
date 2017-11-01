package com.salton123.appxmly.fm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.OneToNContract;
import com.salton123.appxmly.business.OneToNPresenter;
import com.salton123.appxmly.view.EndLessOnScrollListener;
import com.salton123.appxmly.view.adapter.MrGuoTrackAdapter;
import com.salton123.appxmly.wrap.XmlyInitializer;
import com.salton123.appxmly.wrapper.XmPlayerStatusAdapter;
import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.util.EventUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:17
 * Time: 10:17
 * Description:
 */
public class SearchedTracksFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.SearchedTracksFmIView {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private MrGuoTrackAdapter mAdapter;
    private int categoryId = 0;
    private int page = 1;
    private int pageSize = 20;
    private String keyword = "郭德纲";

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

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new OneToNPresenter();
        keyword = getArguments().getString(ARG_ITEM);
        XmlyInitializer.getInstance().addPlayerStatusListener(listener);
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
                mPresenter.getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
            }
        });
        recycler.setAdapter(mAdapter);
        mPresenter.getSearchedTracks(keyword, categoryId, 2, page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                // PlayableModel playableModel =
                Track album = mAdapter.getItem(position);
                // xmUtils.saveAlbum(_mActivity, album);
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(TracksMultiTypeFragment.class, album)));
                // mPlayerManager.playList(mAdapter.getData(), position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XmlyInitializer.getInstance().removePlayerStatusListener(listener);
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


    // public void getSearchedTracks(String keyword, int categoryId, int calc_dimension, int page, int pageSize) {
    //
    //     ApiMethod.getSearchedTracks(keyword, calc_dimension, categoryId, page, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SearchTrackList>() {
    //         @Override
    //         public void accept(SearchTrackList searchTrackList) throws Exception {
    //             onSearchedTracksData(searchTrackList);
    //
    //         }
    //     }, new Consumer<Throwable>() {
    //         @Override
    //         public void accept(Throwable throwable) throws Exception {
    //             onSearchedTracksError(0, throwable.getMessage());
    //         }
    //     });
    // }

    @Override
    public void onSearchedTracksData(SearchTrackList searchTrackList) {
        if (searchTrackList.getTracks().size() < pageSize) {
            toast("数据加载完毕");
        }
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
//            page = 1;
            mAdapter.clear();
            mAdapter.setData(searchTrackList.getTracks());
        } else {
            mAdapter.addMoreData(searchTrackList.getTracks());
        }
    }

    @Override
    public void onSearchedTracksError(int resCode, String errorMsg) {
        toast(errorMsg);
    }
}
