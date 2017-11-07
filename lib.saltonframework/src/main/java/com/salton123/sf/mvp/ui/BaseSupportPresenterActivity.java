package com.salton123.sf.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.salton123.sf.base.BaseSupportActivity;
import com.salton123.sf.mvp.presenter.BasePresenter;
import com.salton123.sf.mvp.view.BaseView;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/8 21:44
 * ModifyTime: 21:44
 * Description:
 */
public abstract class BaseSupportPresenterActivity<T extends BasePresenter> extends BaseSupportActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }
}
