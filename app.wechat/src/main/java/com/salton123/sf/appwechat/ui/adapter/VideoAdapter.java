package com.salton123.sf.appwechat.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.DraweeView;
import com.salton123.sf.appstub.FrescoImageLoader;
import com.salton123.sf.appwechat.R;
import com.salton123.sf.appwechat.model.entity.kaiyan.DataBean;
import com.salton123.sf.appwechat.model.entity.kaiyan.ItemListBean;
import com.salton123.sf.util.DateUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;


public class VideoAdapter extends BGARecyclerViewAdapter<ItemListBean> {
    public VideoAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

//    public VideoAdapter(RecyclerView recyclerView) {
//        super(recyclerView, R.layout.video_item);
//    }


    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getType().equals("video")) {
            return R.layout.video_item;
        } else return R.layout.null_item;
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ItemListBean model) {
        if (getItem(position).getType().equals("video")) {
            DataBean dataBean = model.getData();
            if (dataBean != null) {
                FrescoImageLoader.display((DraweeView) helper.getView(R.id.img), dataBean.getCover() != null ? dataBean.getCover().getDetail() : "");
                helper.setText(R.id.title, model.getData().getTitle());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("#").append(dataBean.getCategory())
                        .append(" ")
                        .append(" / ")
                        .append(" ")
                        .append(DateUtil.formatTime2(dataBean.getDuration()));
                helper.setText(R.id.description, stringBuilder.toString());
            }
        } else {
            helper.setVisibility(R.id.ll_holder, View.GONE);
        }
    }
}