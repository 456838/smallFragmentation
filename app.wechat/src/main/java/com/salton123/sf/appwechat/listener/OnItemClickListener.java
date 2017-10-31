package com.salton123.sf.appwechat.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnItemClickListener {
    void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
}