package com.salton123.sf.libbase.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.salton123.sf.libbase.R;
import com.salton123.util.BlankUtil;

import org.xutils.common.util.DensityUtil;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/18 15:40
 * Time: 15:40
 * Description:
 */
public class StatusTitleBar extends RelativeLayout {


    private View mView;         //左边View
    private TextView tv_title_back;
    private TextView tv_title;
    private TextView tv_title_additional;
    private ImageView iv_more;

    public StatusTitleBar(Context context) {
        super(context);
        init();
    }

    public StatusTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(72)));
        mView = LayoutInflater.from(getContext()).inflate(R.layout.status_title_bar,this, false);
        tv_title_back = f(R.id.tv_title_back);
        tv_title = f(R.id.tv_title);
        tv_title_additional = f(R.id.tv_title_additional);
        iv_more = f(R.id.iv_more);
//        LayoutParams params = (LayoutParams) mView.getLayoutParams();
//        params.setMargins(0, DensityUtil.dip2px(24),0,0);
//        mView.setLayoutParams(params);
        addView(mView);
    }

    public StatusTitleBar setBackText(CharSequence text, int visibility) {
        if (!BlankUtil.isBlank(text)) tv_title_back.setText(text);
        tv_title_back.setVisibility(visibility);
        return this;
    }

    public StatusTitleBar setBackListener(OnClickListener listener) {
        tv_title_back.setOnClickListener(listener);
        return this;
    }

    public StatusTitleBar setTitleText(CharSequence text, int visibility) {
        if (!BlankUtil.isBlank(text)) tv_title.setText(text);
        tv_title.setVisibility(visibility);
        return this;
    }

    public StatusTitleBar setTitleListener(OnClickListener listener) {
        tv_title.setOnClickListener(listener);
        return this;
    }

    public StatusTitleBar setImage(@DrawableRes int id, int visibility) {
        if (id > 0) iv_more.setImageResource(id);
        iv_more.setVisibility(visibility);
        return this;
    }

    protected <VT extends View> VT f(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }


}
