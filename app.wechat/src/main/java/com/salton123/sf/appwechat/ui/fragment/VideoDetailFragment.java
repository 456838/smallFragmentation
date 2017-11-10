package com.salton123.sf.appwechat.ui.fragment;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salton123.sf.appwechat.R;
import com.salton123.sf.appwechat.model.entity.kaiyan.DataBean;
import com.salton123.sf.base.BaseSupportSwipeBackFragment;
import com.salton123.sf.util.DateUtil;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


public class VideoDetailFragment extends BaseSupportSwipeBackFragment {

    TextView title;
    TextView type;
    TextView description;
    TextView tv_title ;
    JZVideoPlayerStandard videoplayer;
    private DataBean dataBean;

    @Override
    public int GetLayout() {
        return R.layout.video_home_detail_fragment;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        dataBean = getArguments().getParcelable(ARG_ITEM);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void InitViewAndData() {
        title = f(R.id.title);
        type = f(R.id.type);
        description = f(R.id.description);
        videoplayer = f(R.id.videoplayer);
        tv_title = f(R.id.tv_title);
        videoplayer.setUp(dataBean.getPlayUrl(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, dataBean.getCover().getDetail());
        videoplayer.thumbImageView.setImageURI(Uri.parse(dataBean.getCover().getDetail()));
        title.setText(dataBean.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#").append(dataBean.getCategory())
                .append(" ")
                .append(" / ")
                .append(" ")
                .append(DateUtil.formatTime2(dataBean.getDuration()));
        type.setText(stringBuilder.toString());
        description.setText(dataBean.getDescription());
        tv_title.setText(dataBean.getCategory());
    }

    @Override
    public void InitListener() {
        f(R.id.tv_title_back).setVisibility(View.VISIBLE);
        f(R.id.tv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_PLAYING) {
            JZMediaManager.instance().mediaPlayer.pause();
        } else if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE) {
            JZMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        JCMediaManager.instance().mediaPlayer.start();
        if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL || videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_ERROR) {
//            JCMediaManager.instance().mediaPlayer.start();
        } else if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_PLAYING) {
            JZMediaManager.instance().mediaPlayer.pause();
        } else if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE) {
            JZMediaManager.instance().mediaPlayer.start();
        } else if (videoplayer.currentState == JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE) {
            JZMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        hideSoftInput();
        JZVideoPlayer.releaseAllVideos();
    }
}
