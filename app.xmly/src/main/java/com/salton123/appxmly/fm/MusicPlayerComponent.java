package com.salton123.appxmly.fm;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.MusicPlayerContract;
import com.salton123.appxmly.fm.music.BaseMusicPlayerComponent;
import com.salton123.appxmly.wrap.XmlyInitializer;
import com.salton123.appxmly.wrapper.TrackUtil;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.sf.libbase.widget.ShadowImageView;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;


/**
 * User: newSalton@outlook.com
 * Date: 2017/9/16 10:52
 * ModifyTime: 10:52
 * Description:
 */
@SuppressLint("ValidFragment")
public class MusicPlayerComponent extends BaseMusicPlayerComponent implements MusicPlayerContract.IView {
    private ShadowImageView image_view_album;
    private TextView text_view_progress, text_view_duration;
    private AppCompatSeekBar seek_bar;
    private AppCompatImageView button_play_last, button_play_next, button_play_mode_toggle;

    @Override
    public int GetLayout() {
        return R.layout.component_music_player;
    }

    @SuppressLint("ValidFragment")
    private MusicPlayerComponent(MusicPlayerContract.IPresenter musicPlayerPresenter) {
        mPresenter = musicPlayerPresenter;
    }

    public static MusicPlayerComponent newInstance(MusicPlayerContract.IPresenter musicPlayerPresenter) {
        MusicPlayerComponent musicPlayerComponent = new MusicPlayerComponent(musicPlayerPresenter);
        return musicPlayerComponent;
    }

    @Subscribe
    public void onTrack(Track track) {
        super.onTrack(track);
        image_view_album.startRotateAnimation();
        FrescoImageLoader.display(image_view_album, track.getCoverUrlLarge());
        seek_bar.setMax(XmlyInitializer.getInstance().getPlayerManager().getDuration());
        mTracks = XmlyInitializer.getInstance().getPlayerManager().getPlayList();
    }

    @Override
    public void InitViewAndData() {
        super.InitViewAndData();
        image_view_album = f(R.id.image_view_album);
        seek_bar = f(R.id.seek_bar);
        button_play_last = f(R.id.button_play_last);
        button_play_next = f(R.id.button_play_next);
        button_play_mode_toggle = f(R.id.button_play_mode_toggle);
        text_view_progress = f(R.id.text_view_progress);
        text_view_duration = f(R.id.text_view_duration);
        seek_bar.setMax(XmlyInitializer.getInstance().getPlayerManager().getDuration());
    }

    @Override
    public void InitListener() {
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                XmlyInitializer.getInstance().getPlayerManager().seekTo(seekBar.getProgress());
            }
        });
        mini_player_play_pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (XmlyInitializer.getInstance().getPlayerManager().isPlaying()) {
                    XmlyInitializer.getInstance().getPlayerManager().pause();
                } else {
                    XmlyInitializer.getInstance().getPlayerManager().play();
                }
            }
        });
        button_play_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XmlyInitializer.getInstance().getPlayerManager().playNext();
                int index = XmlyInitializer.getInstance().getPlayerManager().getCurrentIndex();
                onTrack(XmlyInitializer.getInstance().getPlayerManager().getTrack(index));
            }
        });
        button_play_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XmlyInitializer.getInstance().getPlayerManager().playPre();
                int index = XmlyInitializer.getInstance().getPlayerManager().getCurrentIndex();
                onTrack(XmlyInitializer.getInstance().getPlayerManager().getTrack(index));
            }
        });
        button_play_mode_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XmPlayListControl.PlayMode current = XmlyInitializer.getInstance().getPlayerManager().getPlayMode();
                XmPlayListControl.PlayMode newMode = TrackUtil.switchNextMode(current);
                XmlyInitializer.getInstance().getPlayerManager().setPlayMode(newMode);
                updatePlayMode(newMode);
            }
        });
    }


    @Override
    public void onPlayStart() {
        super.onPlayStart();
        image_view_album.resumeRotateAnimation();
    }

    @Override
    public void onPlayPause() {
        super.onPlayPause();
        image_view_album.pauseRotateAnimation();
    }

    @Override
    public void onPlayStop() {
        super.onPlayStop();
        image_view_album.cancelRotateAnimation();
    }

    @Override
    public void onSoundPlayComplete() {
        super.onSoundPlayComplete();
        image_view_album.cancelRotateAnimation();
    }

    @Override
    public void onSoundPrepared() {
    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curMode) {
        image_view_album.startRotateAnimation();
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
        seek_bar.setMax(duration);
        text_view_duration.setText(formatDuration(duration));
        text_view_progress.setText(formatDuration(currPos));
        seek_bar.setProgress(currPos);
    }

    @Override
    public boolean onError(XmPlayerException e) {
        image_view_album.cancelRotateAnimation();
        return false;
    }

    @Override
    public void loadTrackList(List<Track> tracks) {
        mTracks = tracks;
    }

    public void updatePlayMode(XmPlayListControl.PlayMode playMode) {
        if (playMode == null) {
            playMode = PLAY_MODEL_LIST_LOOP;
        }
        switch (playMode) {
            case PLAY_MODEL_LIST:
                button_play_mode_toggle.setImageResource(R.drawable.ic_play_mode_list);
                break;
            case PLAY_MODEL_LIST_LOOP:
                button_play_mode_toggle.setImageResource(R.drawable.ic_play_mode_loop);
                break;
            case PLAY_MODEL_RANDOM:
                button_play_mode_toggle.setImageResource(R.drawable.ic_play_mode_shuffle);
                break;
            case PLAY_MODEL_SINGLE_LOOP:
            case PLAY_MODEL_SINGLE:
                button_play_mode_toggle.setImageResource(R.drawable.ic_play_mode_single);
                break;
        }
    }


    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }
}
