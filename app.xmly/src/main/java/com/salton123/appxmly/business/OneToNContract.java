package com.salton123.appxmly.business;

import android.support.v4.app.Fragment;
import android.util.Pair;

import com.salton123.appxmly.model.HotVoiceItem;
import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/8 17:25
 * ModifyTime: 17:25
 * Description:
 */
public interface OneToNContract {

    interface IView extends BaseView {
        void onProvinces();

    }

    interface AlbumListIView extends BaseView {
        void showAlbum(AlbumList list);

        void onShowAlbumError(int resCode, String errorMsg);
    }

    interface IndexFmIView extends BaseView {
        void onIndexFmData(List<Pair<Fragment, String>> data);

        void onIndexFmError(String msg);
    }

    interface SearchedTracksFmIView extends BaseView {
        void onSearchedTracksData(SearchTrackList searchTrackList);

        void onSearchedTracksError(int resCode, String errorMsg);
    }

    interface TracksFmIView extends BaseView {

        void showTracks(TrackList list);

        void onShowTracksError(int resCode, String errorMsg);
    }

    interface HotVoiceIView extends BaseView{
        void onHotVoiceShow(List<HotVoiceItem> hotVoiceItems);
    }


    interface Presenter extends BasePresenter<IView> {
        /**
         * @param tagName        分类下对应的专辑标签，不填则为热门分类
         * @param categoryId     分类ID，指定分类，为0时表示热门分类
         * @param calc_dimension 计算维度，现支持最火（1），最新（2），经典或播放最多（3）
         * @param page           返回第几页，必须大于等于1，不填默认为1
         * @param pageSize       每页多少条，默认20，最多不超过200
         */
        void getAlbumList(String tagName, int categoryId, int calc_dimension, int page, int pageSize);

        void getIndexFmData();

        /**
         * @param keyword        搜索关键词
         * @param categoryId     分类ID，不填或者为0检索全库
         * @param calc_dimension 排序条件：2-最新，3-最多播放，4-最相关（默认）
         * @param page           返回第几页，必须大于等于1，不填默认为1
         * @param pageSize       每页多少条，默认20，最多不超过200
         */
        void getSearchedTracks(String keyword, int categoryId, int calc_dimension, int page, int pageSize);

        /**
         * @param album_id 专辑ID
         * @param sort     sort String 否 “asc”表示喜马拉雅正序，”desc”表示喜马拉雅倒序，”time_asc”表示时间升序，”time_desc”表示时间降序，默认为”asc”
         * @param page     返回第几页，必须大于等于1，不填默认为1
         * @param pageSize 每页多少条，默认20，最多不超过200
         */
        void getTracks(long album_id, String sort, int page, int pageSize);

        /**
         * 热门声音
         */
        void getHotVoice();


    }
}
