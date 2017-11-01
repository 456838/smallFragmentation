package com.salton123.appxmly.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.DraweeView;
import com.salton123.appxmly.R;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.util.DateUtils;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:49
 * Time: 10:49
 * Description:
 */
public class AlbumAdapter extends BGARecyclerViewAdapter<Album> {
    public AlbumAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_mr_guo);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Album model) {

        helper
                .setText(R.id.author, model.getAnnouncer().getNickname() + "")
                .setText(R.id.track_title, model.getAlbumTitle() + "")
                .setText(R.id.track_tags, "-" + DateUtils.getDateTime(model.getUpdatedAt()) + "-")
                .setText(R.id.subhead, "新作：" + model.getLastUptrack() == null ? "" : model.getLastUptrack().getTrackTitle())
//                .setText(R.id.post_time, model.getAlbumIntro() + "")
 ;
        if (model.getLastUptrack() == null) helper.getView(R.id.subhead).setVisibility(View.GONE);
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.sdv_thumbnail), model.getCoverUrlLarge());
//        LogUtils.e("aa" + new GsonBuilder().setPrettyPrinting().create().toJson(model));
    }
}
