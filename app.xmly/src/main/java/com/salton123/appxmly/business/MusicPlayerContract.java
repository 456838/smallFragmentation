package com.salton123.appxmly.business;

import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.List;


/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/12/16
 * Time: 8:27 AM
 * Desc: MusicPlayerContract
 */
public interface MusicPlayerContract {


    interface IView extends BaseView {

        void onPlayStart();

        void onPlayPause();

        void onPlayStop();

        void onSoundPlayComplete();

        void onSoundPrepared();

        void onSoundSwitch(PlayableModel lastModel, PlayableModel curMode);

        void onBufferingStart();

        void onBufferingStop();

        void onBufferProgress(int i);

        void onPlayProgress(int currPos, int duration);

        boolean onError(XmPlayerException e);

        void loadTrackList(List<Track> tracks);
    }


    interface IPresenter extends BasePresenter<IView>{
        void loadCachedTrackList();
    }
}
