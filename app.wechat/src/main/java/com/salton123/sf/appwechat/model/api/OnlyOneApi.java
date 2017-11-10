package com.salton123.sf.appwechat.model.api;


import com.salton123.sf.appwechat.model.entity.kaiyan.VideoListBean;
import com.salton123.sf.config.RetrofitManager;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/10 15:41
 * Time: 15:41
 * Description:
 */

public class OnlyOneApi {

    public interface KyApiService {

        @GET("tabs/selected")
        Observable<VideoListBean> getVideoList(@Query("date") String date);
    }

    public static final String KAIYAN_HOST = "https://baobab.kaiyanapp.com/api/v4/";
    public static final String HOT_MUSIC = "http://mapi.yinyuetai.com/video/";
    public static final String deviceinfo = "{\"aid\":\"10201036\",\"os\":\"Android\","
            + "\"ov\":" + "\"" + android.os.Build.VERSION.RELEASE + "\"" + ","
            + "\"rn\":\"720*1280\","
            + "\"dn\":" + "\"" + android.os.Build.MODEL + "\"" + ","
            + "\"cr\":\"46000\","
            + "\"as\":"
            + "\"WIFI\","
            + "\"uid\":"
            + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
            + "\"clid\":110025000}";


    public static KyApiService GetKyApiService() {
        return RetrofitManager.getRetrofit(KAIYAN_HOST).create(KyApiService.class);
    }

}
