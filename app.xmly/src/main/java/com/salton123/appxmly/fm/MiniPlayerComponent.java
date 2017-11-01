package com.salton123.appxmly.fm;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.salton123.appxmly.R;
import com.salton123.appxmly.business.MusicPlayerContract;
import com.salton123.appxmly.fm.music.BaseMusicPlayerComponent;
import com.salton123.appxmly.wrap.XmlyInitializer;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;


/**
 * User: newSalton@outlook.com
 * Date: 2017/8/11 16:17
 * ModifyTime: 16:17
 * Description:
 */
public class MiniPlayerComponent extends BaseMusicPlayerComponent implements MusicPlayerContract.IView {
    public AppCompatImageView mini_player_play_list;
    @SuppressLint("ValidFragment")
    private MiniPlayerComponent(MusicPlayerContract.IPresenter musicPlayerPresenter) {
        mPresenter = musicPlayerPresenter;
    }

    public MiniPlayerComponent(){}
    public static MiniPlayerComponent newInstance(MusicPlayerContract.IPresenter musicPlayerPresenter) {
        MiniPlayerComponent musicPlayerComponent = new MiniPlayerComponent(musicPlayerPresenter);
        return musicPlayerComponent;
    }

    @Override
    public int GetLayout() {
        return R.layout.fm_mini_player;
    }

    @Override
    public void InitViewAndData() {
        super.InitViewAndData();
        mini_player_play_list = f(R.id.mini_player_play_list);
    }

    @Override
    public void InitListener() {
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
        mini_player_play_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void loadTrackList(List<Track> tracks) {
        mTracks = tracks;
    }

}
