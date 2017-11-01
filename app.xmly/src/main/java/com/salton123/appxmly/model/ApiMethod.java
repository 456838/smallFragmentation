package com.salton123.appxmly.model;

import com.google.gson.GsonBuilder;
import com.salton123.appxmly.wrap.ApiException;
import com.salton123.callback.HttpResponseHandler;
import com.salton123.mvp.util.RxUtil;
import com.salton123.util.FileUtils;
import com.salton123.util.log.MLog;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.live.radio.RadioList;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.BatchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 16:18
 * Time: 16:18
 * Description:
 */
public class ApiMethod {
    private static final String TAG = "ApiMethod";

    public static Observable<CategoryList> getCategory() {
        return Observable.create(new ObservableOnSubscribe<CategoryList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<CategoryList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();        //不需要参数
                CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
                    @Override
                    public void onSuccess(CategoryList categoryList) {
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(categoryList);
                        FileUtils.writeFile("/sdcard/z01/getCategory.txt",gsonStr);
                        emitter.onNext(categoryList);
                    }

                    @Override
                    public void onError(int i, String s) {

                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    public static Observable<TagList> getTag(final long cagetoryId) {
        return Observable.create(new ObservableOnSubscribe<TagList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TagList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, cagetoryId + "");
                map.put(DTransferConstants.TYPE, 0 + "");
                CommonRequest.getTags(map, new IDataCallBack<TagList>() {

                    @Override
                    public void onSuccess(TagList tagList) {
                        emitter.onNext(tagList);
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(tagList);
                        FileUtils.writeFile("/sdcard/z01/getTag.txt",gsonStr);
                    }

                    @Override
                    public void onError(int i, String s) {

                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    public static Observable<Category> getFrom(final CategoryList categoryList) {
        if (categoryList==null ||categoryList.getCategories()==null)return null ;
        return Observable.fromArray(categoryList.getCategories().toArray(new Category[categoryList.getCategories().size()]));
    }

    public static Observable<TagList> test() {
        return getCategory().flatMap(new Function<CategoryList, ObservableSource<Category>>() {
            @Override
            public ObservableSource<Category> apply(@NonNull CategoryList categoryList) throws Exception {
                return getFrom(categoryList);
            }
        }).flatMap(new Function<Category, ObservableSource<TagList>>() {
            @Override
            public ObservableSource<TagList> apply(@NonNull Category category) throws Exception {
                return getTag(category.getId());
            }
        });
    }


    /**
     * 根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）
     * @param category_id 分类ID，指定分类，为0时表示热门分类
     * @param tag_name 分类下对应的专辑标签，不填则为热门分类
     * @param calc_dimension 计算维度，现支持最火（1），最新（2），经典或播放最多（3）
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @param count 每页多少条，默认20，最多不超过200
     * @return
     */
    public static Observable<AlbumList> getAlbumList(final int category_id, final String tag_name
            , final int calc_dimension, final int page , final int count) {
        return Observable.create(new ObservableOnSubscribe<AlbumList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<AlbumList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, category_id+"");
                map.put(DTransferConstants.TAG_NAME, tag_name);
                map.put(DTransferConstants.CALC_DIMENSION, calc_dimension+"");
                map.put(DTransferConstants.PAGE, page+"");
                map.put(DTransferConstants.PAGE_SIZE, count+"");
                CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
                    @Override
                    public void onSuccess(AlbumList albumList) {
//                        MLog.debug(TAG,"[getAlbumList] onSuccess albumList="+albumList);
                        emitter.onNext(albumList);
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(albumList);
                        FileUtils.writeFile("/sdcard/z01/getAlbumList.txt",gsonStr);

                    }

                    @Override
                    public void onError(int i, String s) {
                        MLog.error(TAG,"[getAlbumList] onError code="+i+",msg="+s);
                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    /**
     *
     * sort String 否 “asc”表示喜马拉雅正序，”desc”表示喜马拉雅倒序，”time_asc”表示时间升序，”time_desc”表示时间降序，默认为”asc”
     * @param albumId
     * @param sort
     * @param page
     * @return
     */
    public static Observable<TrackList> getTracks(final String albumId, final String sort, final int page, final int pageSize) {
        return Observable.create(new ObservableOnSubscribe<TrackList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TrackList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.ALBUM_ID, albumId);
                map.put(DTransferConstants.SORT, sort);
                map.put(DTransferConstants.PAGE,page + "");
                map.put(DTransferConstants.PAGE_SIZE, pageSize+"");
                CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {

                    @Override
                    public void onSuccess(TrackList trackList) {
                        emitter.onNext(trackList);
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(trackList);
                        FileUtils.writeFile("/sdcard/z01/getTracks.txt",gsonStr);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }

        });
    }

    public void getTracksTest(final HttpResponseHandler<String> handler){
        getTracks("203355","time_desc",1,20).compose(RxUtil.<TrackList>rxSchedulerHelper()).subscribe(new Consumer<TrackList>() {
            @Override
            public void accept(TrackList trackList) throws Exception {
                String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(trackList);
//                FileUtils.writeFile("/sdcard/z01/getSearchedAlbums.txt",gsonStr);
                handler.onSuccess(gsonStr);
            }
        });
    }
    /**
     * 搜索专辑
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<SearchAlbumList> getSearchedAlbums(final String keyword, final String category, final int page){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.PAGE, page+"");
                CommonRequest.getSearchedAlbums(map, new IDataCallBack<SearchAlbumList>(){

                    @Override
                    public void onSuccess(SearchAlbumList searchAlbumList) {
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(searchAlbumList);
                        FileUtils.writeFile("/sdcard/z01/getSearchedAlbums.txt",gsonStr);
                        emitter.onNext(searchAlbumList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    public static void getSearchedAlbumsTest(final HttpResponseHandler<String> handler){
        getSearchedAlbums("段子来了","0",1).compose(RxUtil.<SearchAlbumList>rxSchedulerHelper()).subscribe(new Consumer<SearchAlbumList>() {
            @Override
            public void accept(SearchAlbumList searchAlbumList) throws Exception {
                String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(searchAlbumList);
//                FileUtils.writeFile("/sdcard/z01/getSearchedAlbums.txt",gsonStr);
                handler.onSuccess(gsonStr);
            }
        });
    }


    /**
     *
     * 搜索声音
     * @param keyword 搜索关键词
     * @param  calc_demension 排序条件：2-最新，3-最多播放，4-最相关（默认）
     * @param category_id  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @param  count 每页多少条，默认20，最多不超过200
     * @return
     */
    public static Observable<SearchTrackList> getSearchedTracks(final String keyword, final int calc_demension, final int category_id, final int page, final int count){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CALC_DIMENSION, calc_demension+"");
                map.put(DTransferConstants.CATEGORY_ID, category_id+"");
                map.put(DTransferConstants.PAGE, page + "");
                map.put(DTransferConstants.PAGE_SIZE, count + "");
                CommonRequest.getSearchedTracks(map, new IDataCallBack<SearchTrackList>() {
                    @Override
                    public void onSuccess(SearchTrackList searchTrackList) {
//                        MLog.debug(TAG,"[getSearchedTracks] onSuccess searchTrackList="+searchTrackList);
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(searchTrackList);
                        FileUtils.writeFile("/sdcard/z01/getSearchedTracks.txt",gsonStr);
                        emitter.onNext(searchTrackList);
                    }

                    @Override
                    public void onError(int i, String s) {
//                        MLog.info(TAG,"[getSearchedTracks] onError code="+i+",msg= "+s);
                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    /**
     *
     * 搜索声音
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<RadioList> getSearchedRadios(final String keyword, final String category, final int page){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.PAGE, page + "");
                CommonRequest.getSearchedRadios(map, new IDataCallBack<RadioList>() {
                    @Override
                    public void onSuccess(RadioList radioList) {
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(radioList);
                        FileUtils.writeFile("/sdcard/z01/getSearchedRadios.txt",gsonStr);
                        emitter.onNext(radioList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }

    /**
     * 批量获取声音列表
     * ids 	String 	是 	声音ID列表 最大ID数量为200个，超过200的ID将忽略
     * @param track_ids
     * @return
     */
    public static Observable getBatchTracks(final String track_ids){
        return  Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter observableEmitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.TRACK_IDS, track_ids);
                CommonRequest.getBatchTracks(map, new IDataCallBack<BatchTrackList>() {
                    @Override
                    public void onSuccess(BatchTrackList batchTrackList) {
                        String gsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(batchTrackList);
                        FileUtils.writeFile("/sdcard/z01/getBatchTracks.txt",gsonStr);
                        observableEmitter.onNext(batchTrackList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        observableEmitter.onError(new ApiException(new ApiExEntity(i,s)));
                    }
                });
            }
        });
    }
}
