package com.salton123.sf.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.salton123.sf.base.BaseSupportFragment;
import com.salton123.sf.mvp.presenter.BasePresenter;
import com.salton123.sf.mvp.view.BaseView;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 15:22
 * Time: 15:22
 * Description:
 */
public abstract class BaseSupportPresenterFragment<T extends BasePresenter> extends BaseSupportFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

}
