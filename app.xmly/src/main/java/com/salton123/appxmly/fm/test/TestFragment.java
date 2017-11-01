package com.salton123.appxmly.fm.test;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.base.BaseSupportFragment;
import com.salton123.util.ScreenUtils;

import static com.salton123.util.ScreenUtils.pxToDp;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/22 11:40
 * Time: 11:40
 * Description:
 */
public class TestFragment extends BaseSupportFragment {
    TextView mBottom_Bar_Video_Join;

    @Override
    public int GetLayout() {
        return R.layout.fm_test;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        mBottom_Bar_Video_Join = f(R.id.bottom_bar_video_join);
        showLoadingAnimation(mBottom_Bar_Video_Join);
        mBottom_Bar_Video_Join.setText("加载玩法中");
    }

    @Override
    public void InitListener() {
        float a = ScreenUtils.pxToDp(_mActivity,436);
        float b = pxToDp(_mActivity,776);
        System.out.println("a:"+a+",b:"+b);
    }

    private void showLoadingAnimation(final TextView view) {
        final AnimationDrawable mLoadingAnimationDrawable;
        mLoadingAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.sticker_loading_animation_list);
        mLoadingAnimationDrawable.setBounds(mLoadingAnimationDrawable.getBounds().left, mLoadingAnimationDrawable.getBounds().top, (int) ScreenUtils.dpToPx(_mActivity,25), (int)ScreenUtils.dpToPx(_mActivity,25));
        view.setCompoundDrawables(mLoadingAnimationDrawable, null, null, null);
        view.post(new Runnable() {
            @Override
            public void run() {
                mLoadingAnimationDrawable.start();
            }
        });
    }

    private void dismissLoadingAnimation(final TextView view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setCompoundDrawables(null, null, null, null);
            }
        });
    }
}
