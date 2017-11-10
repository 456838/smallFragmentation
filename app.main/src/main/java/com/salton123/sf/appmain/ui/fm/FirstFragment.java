package com.salton123.sf.appmain.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.salton123.sf.appmain.R;
import com.salton123.sf.base.BaseSupportFragment;

import net.wequick.small.Small;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/15 11:35
 * Time: 11:35
 * Description:
 */
public class FirstFragment extends BaseSupportFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {

    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
//            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(VideoFragment.class));
            SupportFragment fragment = Small.createObject("fragment-v4","wechat/video" , _mActivity);
            loadRootFragment(R.id.fl_container,fragment);
        }
    }
}
