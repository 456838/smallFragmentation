package com.salton123.appxmly;

import android.content.Context;

import com.google.gson.Gson;
import com.salton123.util.PreferencesUtils;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/31 11:31
 * ModifyTime: 11:31
 * Description:
 */
public class xmUtils {

    public static boolean saveAlbum(Context context, Album album) {
        return PreferencesUtils.putString(context, "album", new Gson().toJson(album));
    }

    public static Album getAlbum(Context context) {
        try{
            String AlbumStr = PreferencesUtils.getString(context, "album");
            return new Gson().fromJson(AlbumStr, Album.class);
        }catch (Exception e){
            return null ;
        }

    }
}
