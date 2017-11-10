package com.salton123.sf.appwechat.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.sf.appwechat.R;
import com.salton123.sf.appwechat.business.video.VideoFragmentContract;
import com.salton123.sf.appwechat.business.video.VideoFragmentPresenter;
import com.salton123.sf.appwechat.event.StartBrotherEvent;
import com.salton123.sf.appwechat.model.entity.kaiyan.ItemListBean;
import com.salton123.sf.appwechat.model.entity.kaiyan.VideoListBean;
import com.salton123.sf.appwechat.ui.adapter.VideoAdapter;
import com.salton123.sf.appwechat.ui.callback.EndLessOnScrollListener;
import com.salton123.sf.libbase.widget.StatusTitleBar;
import com.salton123.sf.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.sf.util.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * User: newSalton@outlook.com
 * Date: 2017/11/9 11:42
 * ModifyTime: 11:42
 * Description:
 */
public class VideoFragment extends BaseSupportPresenterFragment<VideoFragmentPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate, VideoFragmentContract.View {
    RecyclerView recycler;
    BGARefreshLayout bgaRefresh;
    StatusTitleBar mHeaderView;
    private String date;
    private VideoAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    public int GetLayout() {
        return R.layout.video_home_fragment;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new VideoFragmentPresenter();
    }

    @Override
    public void InitListener() {
        EventBus.getDefault().register(this);
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ItemListBean videoListBean = mAdapter.getItem(position);
                EventBus.getDefault().post(new StartBrotherEvent(newInstance(VideoDetailFragment.class, videoListBean.getData())));
            }
        });
        recycler.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager, 0) {
            @Override
            public void onLoadMore() {
                mPresenter.getData(date);
            }
        });
        bgaRefresh.setDelegate(this);
    }

    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void InitViewAndData() {
        recycler = f(R.id.recycler);
        bgaRefresh = f(R.id.bgaRefresh);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(mLinearLayoutManager);
        mAdapter = new VideoAdapter(recycler);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(_mActivity, true);
        // 设置下拉刷新和上拉加载更多的风格
        bgaRefresh.setRefreshViewHolder(refreshViewHolder);
        recycler.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        int statusHeight = ScreenUtils.getStatusHeight(_mActivity);
        System.out.println("statusHeight:" + statusHeight);
        mHeaderView = (StatusTitleBar) inflater().inflate(R.layout.simple_title_layout, null);
        mHeaderView.setTitleText("热门", View.VISIBLE).setTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("hello");
            }
        });
        mAdapter.addHeaderView(mHeaderView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getData(date);
    }



    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.clear();
        date = "";
        mPresenter.getData(date);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        mPresenter.getData(date);
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {
        bgaRefresh.endRefreshing();
//        bgaRefresh.endLoadingMore();
    }

    @Override
    public void showData(VideoListBean datalist) {
        mAdapter.addMoreData(datalist.getItemList());
        int end = datalist.getNextPageUrl().lastIndexOf("&num");
        int start = datalist.getNextPageUrl().lastIndexOf("date=");
        date = datalist.getNextPageUrl().substring(start + 5, end);
//        dismissLoading();
    }

    @Override
    public void showError(String errorMsg) {
        toast(errorMsg);
    }
}
