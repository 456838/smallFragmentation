package com.salton123.appxmly.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.DraweeView;
import com.salton123.appxmly.R;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.util.DateUtils;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:49
 * Time: 10:49
 * Description:
 */
public class MrGuoTrackAdapter extends BGARecyclerViewAdapter<Track> {
    public MrGuoTrackAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_mr_guo);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Track model) {
        helper
                .setText(R.id.author, model.getAnnouncer().getNickname() + "")
                .setText(R.id.track_title, model.getTrackTitle() + "")
                .setText(R.id.track_tags, "-" + DateUtils.getDateTime(model.getUpdatedAt()) + "-")
                .setText(R.id.subhead, model.getTrackTags() + "")
//                .setText(R.id.post_time, model.getTrackIntro())
 ;
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.sdv_thumbnail), model.getCoverUrlLarge());
//        LogUtils.e("aa" + new GsonBuilder().setPrettyPrinting().create().toJson(model));
    }
}
