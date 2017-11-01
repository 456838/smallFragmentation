package com.salton123.appxmly.fm.music;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.MusicPlayerContract;
import com.salton123.appxmly.wrap.XmlyInitializer;
import com.salton123.appxmly.wrapper.TrackUtil;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.util.EventUtil;
import com.salton123.util.log.FP;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/21 16:33
 * ModifyTime: 16:33
 * Description:
 */
public abstract class BaseMusicPlayerComponent extends BaseSupportPresenterFragment<MusicPlayerContract.IPresenter> implements IXmPlayerStatusListener {
    public TextView mini_player_title;
    public TextView mini_player_intro;
    public AppCompatImageView mini_player_play_pause_button;

    public List<Track> mTracks;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventUtil.register(this);
    }

    @Subscribe
    public void onTrack(Track track) {
        mini_player_title.setText("" + TrackUtil.getTrackTitle(track));
        mini_player_intro.setText("" + TrackUtil.getTrackIntro(track));
        mTracks = XmlyInitializer.getInstance().getPlayerManager().getPlayList();
    }

    @Override
    public void InitViewAndData() {
        mini_player_intro = f(R.id.mini_player_intro);
        mini_player_title = f(R.id.mini_player_title);
        mini_player_play_pause_button = f(R.id.mini_player_play_pause_button);
        mTracks = TrackUtil.loadTrackList();
        if (!FP.empty(mTracks)){
            Track track = mTracks.get(TrackUtil.getTrackIndex());
            mini_player_title.setText("" + TrackUtil.getTrackTitle(track));
            mini_player_intro.setText("" + TrackUtil.getTrackIntro(track));
            XmlyInitializer.getInstance().getPlayerManager().setPlayList(mTracks,TrackUtil.getTrackIndex());
        }
    }

    @Override
    public void onPlayStart() {
        mini_player_play_pause_button.setImageResource(R.drawable.ic_pause);
    }

    @Override
    public void onPlayPause() {
        mini_player_play_pause_button.setImageResource(R.drawable.ic_play);
    }


    @Override
    public void onPlayStop() {
    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curMode) {
        TrackUtil.saveCurrentTrackIndex();
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {
    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int currPos, int duration) {

    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        XmlyInitializer.getInstance().getPlayerManager().addPlayerStatusListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        XmlyInitializer.getInstance().getPlayerManager().removePlayerStatusListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

}
