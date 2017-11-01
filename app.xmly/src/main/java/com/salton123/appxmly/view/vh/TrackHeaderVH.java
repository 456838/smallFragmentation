package com.salton123.appxmly.view.vh;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.appxmly.R;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.util.ViewUtils;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 21:25
 * ModifyTime: 21:25
 * Description:
 */
public class TrackHeaderVH extends BaseVH {
    private View itemView;
    private SimpleDraweeView sdv_cover_url;
    private TextView tv_album_title, tv_album_id, tv_total_count, tv_album_intro;

    public TrackHeaderVH(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    @Override
    public void initData(Object data) {
        if (data instanceof TrackList) {
            TrackList model = (TrackList) data;
            sdv_cover_url = ViewUtils.f(itemView, R.id.sdv_cover_url);
            tv_album_title = ViewUtils.f(itemView, R.id.tv_album_title);
            tv_album_id = ViewUtils.f(itemView, R.id.tv_album_id);
            tv_total_count = ViewUtils.f(itemView, R.id.tv_total_count);
            tv_album_intro = ViewUtils.f(itemView, R.id.tv_album_intro);
            FrescoImageLoader.display(sdv_cover_url, model.getCoverUrlLarge());
            tv_album_title.setText(model.getAlbumTitle());
            tv_album_id.setText("id:" + model.getAlbumId());
            tv_total_count.setText("总数:" + model.getTotalCount());
            tv_album_intro.setText(model.getAlbumIntro());
        }
    }
}