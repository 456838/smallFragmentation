package com.salton123.appxmly.business;

import android.support.v4.app.Fragment;
import android.util.Pair;

import com.salton123.appxmly.fm.AlbumListFragment;
import com.salton123.appxmly.fm.SearchedTracksFragment;
import com.salton123.appxmly.model.ApiMethod;
import com.salton123.appxmly.model.HotVoiceItem;
import com.salton123.mvp.presenter.RxPresenter;
import com.salton123.mvp.util.RxUtil;
import com.salton123.util.log.MLog;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/8 17:29
 * ModifyTime: 17:29
 * Description:
 */
public class OneToNPresenter extends RxPresenter<OneToNContract.IView> implements OneToNContract.Presenter{
    public static final String TAG = "OneToNPresenter";

//    public OneToNPresenter(){
//        XmlyInitializer.getInstance().addPlayerStatusListener(this);
//    }
    @Override
    public void getAlbumList(String tagName, int categoryId, int calc_dimension, int page, int pageSize) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, categoryId + "");
        map.put(DTransferConstants.TAG_NAME, tagName);
        map.put(DTransferConstants.CALC_DIMENSION, calc_dimension + "");
        map.put(DTransferConstants.PAGE, page + "");
        map.put(DTransferConstants.PAGE_SIZE, pageSize + "");
        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
//                MLog.debug(TAG,"[getAlbumList] onSuccess albumList="+albumList);
                if (mView instanceof OneToNContract.AlbumListIView) {
                    ((OneToNContract.AlbumListIView) mView).showAlbum(albumList);
                }

            }

            @Override
            public void onError(int i, String s) {
                MLog.error(TAG, "[getAlbumList] onError code=" + i + ",msg=" + s);
                ((OneToNContract.AlbumListIView) mView).onShowAlbumError(i, s);
            }
        });
//        ApiMethod.getAlbumList(categoryId, tagName, calc_dimension, page, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<AlbumList>() {
//            @Override
//            public void accept(AlbumList albumList) throws Exception {
//                MLog.info(TAG, new GsonBuilder().setPrettyPrinting().create().toJson(albumList));
//                if (mView instanceof OneToNContract.AlbumListIView) {
//                    ((OneToNContract.AlbumListIView) mView).showAlbum(albumList);
//                }
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                throwable.printStackTrace();
//                ((OneToNContract.AlbumListIView) mView).onShowAlbumError(0, throwable.getMessage());
//            }
//        });
    }

    @Override
    public void getIndexFmData() {
        List<Pair<Fragment, String>> mData = new ArrayList<>();
        mData.add(new Pair<Fragment, String>(AlbumListFragment.newInstance(AlbumListFragment.class, "郭德纲"), "郭德纲"));
        mData.add(new Pair<Fragment, String>(SearchedTracksFragment.newInstance(SearchedTracksFragment.class, "岳云鹏"), "岳云鹏"));
        mData.add(new Pair<Fragment, String>(SearchedTracksFragment.newInstance(SearchedTracksFragment.class, "高晓松"), "高晓松"));
        mData.add(new Pair<Fragment, String>(SearchedTracksFragment.newInstance(SearchedTracksFragment.class, "吴晓波"), "吴晓波"));
        mData.add(new Pair<Fragment, String>(SearchedTracksFragment.newInstance(SearchedTracksFragment.class, "采采"), "段子来了"));
        if (mView instanceof OneToNContract.IndexFmIView) {
            ((OneToNContract.IndexFmIView) mView).onIndexFmData(mData);
        }
    }

    @Override
    public void getSearchedTracks(String keyword, int categoryId, int calc_dimension, int page, int pageSize) {
        MLog.info(TAG, "keyword=" + keyword + ",categoryId=" + categoryId + ",calc_dimension=" + calc_dimension + ",page=" + page + ",pageSize=" + pageSize);
        ApiMethod.getSearchedTracks(keyword, calc_dimension, categoryId, page, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SearchTrackList>() {
            @Override
            public void accept(SearchTrackList searchTrackList) throws Exception {
                if (mView instanceof OneToNContract.SearchedTracksFmIView) {
                    ((OneToNContract.SearchedTracksFmIView) mView).onSearchedTracksData(searchTrackList);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mView instanceof OneToNContract.SearchedTracksFmIView) {
                    ((OneToNContract.SearchedTracksFmIView) mView).onSearchedTracksError(0, throwable.getMessage());
                }
            }
        });
    }

    @Override
    public void getTracks(long album_id, String sort, int page, int pageSize) {
        ApiMethod.getTracks(album_id + "", sort, page, pageSize).compose(RxUtil.<TrackList>rxSchedulerHelper()).subscribe(new Consumer<TrackList>() {
            @Override
            public void accept(TrackList trackList) throws Exception {
                if (mView instanceof OneToNContract.TracksFmIView) {
                    ((OneToNContract.TracksFmIView) mView).showTracks(trackList);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mView instanceof OneToNContract.TracksFmIView) {
                    ((OneToNContract.TracksFmIView) mView).onShowTracksError(0, throwable.getMessage());
                }
            }
        });
    }

    @Override
    public void getHotVoice() {
        List<HotVoiceItem> hotVoiceItems = new ArrayList<>();
        hotVoiceItems.add(new HotVoiceItem("郭德纲", "郭德纲"));
        hotVoiceItems.add(new HotVoiceItem("吴晓波", "吴晓波"));
        hotVoiceItems.add(new HotVoiceItem("高晓松", "高晓松"));
        hotVoiceItems.add(new HotVoiceItem("岳云鹏", "岳云鹏"));
        hotVoiceItems.add(new HotVoiceItem("段子来了", "采采"));
        if (mView instanceof OneToNContract.HotVoiceIView) {
            ((OneToNContract.HotVoiceIView) mView).onHotVoiceShow(hotVoiceItems);
        }
    }

//
//    @Override
//    public void onPlayStart() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onPlayStart();
//        }
//    }
//
//    @Override
//    public void onPlayPause() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onPlayPause();
//        }
//    }
//
//    @Override
//    public void onPlayStop() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onPlayStop();
//        }
//    }
//
//    @Override
//    public void onSoundPlayComplete() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onSoundPlayComplete();
//        }
//    }
//
//    @Override
//    public void onSoundPrepared() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onSoundPrepared();
//        }
//    }
//
//    @Override
//    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onSoundSwitch(playableModel,playableModel1);
//        }
//    }
//
//    @Override
//    public void onBufferingStart() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onBufferingStart();
//        }
//    }
//
//    @Override
//    public void onBufferingStop() {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onBufferingStop();
//        }
//    }
//
//    @Override
//    public void onBufferProgress(int i) {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onBufferProgress(i);
//        }
//    }
//
//    @Override
//    public void onPlayProgress(int i, int i1) {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            ((OneToNContract.PlayerStatusIView) mView).onPlayProgress(i,i1);
//        }
//    }
//
//    @Override
//    public boolean onError(XmPlayerException e) {
//        if (mView instanceof OneToNContract.PlayerStatusIView){
//            return ((OneToNContract.PlayerStatusIView) mView).onError(e);
//        }
//        return false;
//    }
}
