package com.salton123.appxmly.wrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salton123.appxmly.wrap.XmlyInitializer;
import com.salton123.base.ApplicationBase;
import com.salton123.util.PreferencesUtils;
import com.salton123.util.StringUtils;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/18 20:15
 * ModifyTime: 20:15
 * Description:
 */
public class TrackUtil {

    public static String getTrackTitle(Track track) {
        return track == null
                ? "" : !StringUtils.isEmpty(track.getTrackTitle())
                ? track.getTrackTitle() : !StringUtils.isEmpty(track.getRadioName())
                ? track.getRadioName() : !StringUtils.isEmpty(track.getTemplateName())
                ? track.getTemplateName() : "";
    }

    public static String getTrackIntro(Track track) {
        return track == null
                ? "" : !StringUtils.isEmpty(track.getTrackIntro())
                ? track.getTrackIntro() : !StringUtils.isEmpty(track.getTrackTags())
                ? track.getTrackTags() : "";
    }

    public static void saveTrackList(List<Track> tracks) {
        String trackString = new Gson().toJson(tracks);
        PreferencesUtils.putString(ApplicationBase.mInstance,"currentTrackList",trackString);
        //TODO
    }

    public static List<Track> loadTrackList() {
        //TODO
        String trackString = PreferencesUtils.getString(ApplicationBase.mInstance,"currentTrackList");
        return new Gson().fromJson(trackString,new TypeToken<List<Track>>(){}.getType());
    }

    public static XmPlayListControl.PlayMode switchNextMode(XmPlayListControl.PlayMode current) {
        if (current == null) return PLAY_MODEL_LIST_LOOP;
        switch (current) {
            case PLAY_MODEL_LIST:
                return PLAY_MODEL_LIST_LOOP;
            case PLAY_MODEL_LIST_LOOP:
                return PLAY_MODEL_RANDOM;
            case PLAY_MODEL_RANDOM:
                return PLAY_MODEL_SINGLE_LOOP;
            case PLAY_MODEL_SINGLE_LOOP:
                return PLAY_MODEL_LIST;
            case PLAY_MODEL_SINGLE:
                return PLAY_MODEL_LIST;
        }
        return PLAY_MODEL_LIST_LOOP;
    }

    public static void saveTrackIndex(int index) {
        PreferencesUtils.putInt(ApplicationBase.mInstance,"currentTrackIndex",index);
    }

    public static void saveCurrentTrackIndex(){
        int index = XmlyInitializer.getInstance().getPlayerManager().getCurrentIndex();
        PreferencesUtils.putInt(ApplicationBase.mInstance,"currentTrackIndex",index);
    }

    public static int getTrackIndex(){
        return PreferencesUtils.getInt(ApplicationBase.mInstance,"currentTrackIndex");
    }

}
