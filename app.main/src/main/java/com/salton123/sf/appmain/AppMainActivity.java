package com.salton123.sf.appmain;

import android.os.Bundle;

import com.salton123.sf.appmain.ui.fm.MainFragment;
import com.salton123.sf.base.BaseSupportActivity;
import com.salton123.sf.base.BaseSupportFragment;

public class AppMainActivity extends BaseSupportActivity {
    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(MainFragment.class));
        }

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
//        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks() {
//
//            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
//                LogUtils.d("onFragmentCreated->" + f.toString());
//            }
//
//            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v,
//                                              Bundle savedInstanceState) {
//                LogUtils.d("onFragmentViewCreated->" + f.toString());
//            }
//
//            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
//                LogUtils.d("onFragmentViewDestroyed->" + f.toString());
//            }
//        }, true);

    }

    @Override
    public void InitViewAndData() {

    }

    @Override
    public void InitListener() {

    }
}