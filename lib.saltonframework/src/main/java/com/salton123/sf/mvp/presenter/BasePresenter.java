package com.salton123.sf.mvp.presenter;


import com.salton123.sf.mvp.view.BaseView;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 11:06
 * Time: 11:06
 * Description:
 */
public interface BasePresenter <T extends BaseView>{

    void attachView(T view);

    void detachView();
}
