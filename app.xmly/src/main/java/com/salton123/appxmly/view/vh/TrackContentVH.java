package com.salton123.appxmly.view.vh;

import android.view.View;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.appxmly.wrapper.TrackUtil;
import com.salton123.util.SizeUtils;
import com.salton123.util.ViewUtils;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 21:25
 * ModifyTime: 21:25
 * Description:
 */
public class TrackContentVH extends BaseVH {
    private View itemView;
    private TextView text_view_name,text_view_artist,text_view_duration;
    public TrackContentVH(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    @Override
    public void initData(Object data) {
        if (data instanceof Track){
            Track model = (Track) data;
            text_view_name=  ViewUtils.f(itemView, R.id.text_view_name);
            text_view_artist= ViewUtils.f(itemView,R.id.text_view_artist);
            text_view_duration= ViewUtils.f(itemView,R.id.text_view_duration);
            text_view_name.setText(TrackUtil.getTrackTitle(model)+"");
            text_view_artist.setText(TrackUtil.getTrackIntro(model)+"");
            text_view_duration.setText( SizeUtils.FormetFileSize(model.getDownloadSize()));
        }
    }
}
