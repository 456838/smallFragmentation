package com.salton123.appxmly.view.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 21:30
 * ModifyTime: 21:30
 * Description:
 */
public abstract class BaseVH extends RecyclerView.ViewHolder {
    public BaseVH(View itemView) {
        super(itemView);
    }

    public abstract void initData(Object data);
}
