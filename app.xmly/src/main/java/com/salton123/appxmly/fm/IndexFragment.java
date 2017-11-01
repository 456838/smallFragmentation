package com.salton123.appxmly.fm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.MusicPlayerContract;
import com.salton123.appxmly.business.MusicPlayerPresenter;
import com.salton123.appxmly.business.OneToNContract;
import com.salton123.appxmly.business.OneToNPresenter;
import com.salton123.appxmly.util.TabLayoutUtil;
import com.salton123.appxmly.view.adapter.IndexFragmentAdapter;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.util.LogUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/11 16:14
 * ModifyTime: 16:14
 * Description:
 */

public class IndexFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.IndexFmIView {
    public static final String TAG = "IndexFragment";
    ViewPager vp_hot_music;
    TabLayout tab_hot_music;
    IndexFragmentAdapter adapter;
    FrameLayout music_player_container, mini_player_container;
    SlidingUpPanelLayout slidingUpPanelLayout;
    MiniPlayerComponent miniPlayerComponent;
    MusicPlayerComponent musicPlayerComponent;

    public void setMusicPresenter(MusicPlayerContract.IPresenter musicPresenter) {
        this.musicPresenter = musicPresenter;
    }

    private MusicPlayerContract.IPresenter musicPresenter;

    @Override
    public int GetLayout() {
        return R.layout.fm_index_new;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new OneToNPresenter();
        adapter = new IndexFragmentAdapter(getChildFragmentManager());
        setMusicPresenter(new MusicPlayerPresenter());
    }


    @Override
    public void InitViewAndData() {
        vp_hot_music = f(R.id.vp_index);
        tab_hot_music = f(R.id.tab_hot_music);
        slidingUpPanelLayout = f(R.id.sliding_layout);
        vp_hot_music.setAdapter(adapter);
        vp_hot_music.setCurrentItem(0, false);
        vp_hot_music.setOffscreenPageLimit(5);
        tab_hot_music.setupWithViewPager(vp_hot_music);
        tab_hot_music.setScrollPosition(0, 0, true);
        TabLayoutUtil.dynamicSetTabLayoutMode(tab_hot_music);
        music_player_container = f(R.id.music_player_container);
        mini_player_container = f(R.id.mini_player_container);
        musicPlayerComponent = MusicPlayerComponent.newInstance(musicPresenter);
        miniPlayerComponent = MiniPlayerComponent.newInstance(musicPresenter);
        if (musicPlayerComponent == null) {
            toast("musicPlayerComponent ==null");
        }
        loadRootFragment(R.id.music_player_container, musicPlayerComponent);
        loadRootFragment(R.id.mini_player_container, miniPlayerComponent);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getIndexFmData();
    }

    @Override
    public void InitListener() {
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mini_player_container.setAlpha(1 - slideOffset);
                LogUtils.d("id=" + slidingUpPanelLayout.getId() + ",view id = " + panel.getId() + ",offect=" + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                LogUtils.d("[onPanelStateChanged] view id = " + panel.getId() + ",previousState=" + previousState + ",newState=" + newState);
            }
        });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (miniPlayerComponent != null) {
            if (miniPlayerComponent.getView() != null) {
                miniPlayerComponent.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switchPanel();
                    }
                });
            } else {
                LogUtils.d("miniPlayerComponent.getView()==null");
            }
        } else {
            LogUtils.d("miniPlayerComponent==null");
        }
    }

    @Override
    public void onIndexFmData(List<Pair<Fragment, String>> data) {
        adapter.setData(data);
    }

    @Override
    public void onIndexFmError(String msg) {
        toast(msg);
        mPresenter.getIndexFmData();
    }


    public SlidingUpPanelLayout.PanelState getPanelState() {
        return slidingUpPanelLayout == null ? null : slidingUpPanelLayout.getPanelState();
    }

    public void collapsePanel() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void expandPanel() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    public void switchPanel() {
        if (getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            collapsePanel();
        } else {
            expandPanel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPresenter = null;
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            collapsePanel();
            return true;
        } else {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(_mActivity);
            builder.
                    setTitle("Hi").
                    setMessage(Html.fromHtml("要退出App吗？")).
                    setPositiveButton("嗯", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _mActivity.finish();
                            System.exit(0);
                        }
                    }).setNegativeButton("再看看", null)
                    .show();
            return true;
        }
    }
}
