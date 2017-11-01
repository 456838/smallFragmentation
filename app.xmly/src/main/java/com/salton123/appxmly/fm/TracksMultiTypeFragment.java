package com.salton123.appxmly.fm;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.OneToNContract;
import com.salton123.appxmly.business.OneToNPresenter;
import com.salton123.appxmly.view.EndLessOnScrollListener;
import com.salton123.appxmly.view.adapter.TrackMultiTypeAdapter;
import com.salton123.appxmly.wrapper.TrackUtil;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.sf.libbase.widget.StatusTitleBar;
import com.salton123.util.EventUtil;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.ArrayList;
import java.util.List;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 20:35
 * Time: 20:35
 * Description:
 */
public class TracksMultiTypeFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.TracksFmIView {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private TrackMultiTypeAdapter mAdapter;
    private long album_id = 0;
    private int page = 1;
    private int pageSize = 20;
    private String sort = "asc";
    StatusTitleBar mHeaderView;

    public static <T extends Fragment> T newInstance(Class<T> clz, long value) {
        Bundle bundle = new Bundle();
        bundle.putLong("arg_item", value);
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
    public int GetLayout() {
        return R.layout.fm_tracks;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        Parcelable item = null;
        try {
            item = getArguments().getParcelable(ARG_ITEM);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (item != null && item instanceof Album) {
                Album mAlbum = getArguments().getParcelable(ARG_ITEM);
                if (mAlbum != null) {
                    album_id = mAlbum.getId();
                }
            }else if (item!=null &&item instanceof Track){
                Track track = getArguments().getParcelable(ARG_ITEM);
                if (track!=null){
                    album_id = track.getAlbum().getAlbumId();
                }
            }
            else if (getArguments().getLong(ARG_ITEM) > 0) {
                album_id = getArguments().getLong(ARG_ITEM);
            }
        }
        mPresenter = new OneToNPresenter();
    }


    public static <T extends Fragment> T newInstance(Class<T> clz, @Nullable ArrayList<? extends Parcelable> value) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_ITEM, value);
        T fragment = null;
        try {
            fragment = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void InitViewAndData() {
        refresh = f(R.id.refresh);
        recycler = f(R.id.recycler);
        mAdapter = new <Track>TrackMultiTypeAdapter();
        recycler.setAdapter(mAdapter);
        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getTracks(album_id, sort, page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getTracks(album_id, sort, page++, pageSize);
            }
        });
        mHeaderView = f(R.id.view_status_title_bar);
        mHeaderView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mHeaderView.setTitleText("节目清单", View.VISIBLE).setBackText("返回",View.VISIBLE).setTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toast("hello");
                pop();
            }
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTracks(album_id, sort, page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnItemClickListener(new TrackMultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                List<Track> trackList = mAdapter.getData();
                XmPlayerManager.getInstance(_mActivity).playList(trackList, position);
                EventUtil.sendEvent(trackList.get(position));
                TrackUtil.saveTrackList(trackList);
                TrackUtil.saveCurrentTrackIndex();
            }
        });
    }


    @Override
    public void showTracks(TrackList list) {
        if (list.getTracks().size() < pageSize) toast("数据加载完毕");
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
//            page = 1;
            mAdapter.clear();
            mAdapter.setTrackList(list);
            mAdapter.setData(list.getTracks());
        } else {
            String page = list.getParams().get("page");
            if (page.equals("1")) {
                mAdapter.setTrackList(list);
            }
            mAdapter.addAll(list.getTracks());
        }
    }

    @Override
    public void onShowTracksError(int resCode, String errorMsg) {
        toast(errorMsg);
    }
}
