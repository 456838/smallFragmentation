package com.salton123.sf.appwechat.business.video;


import com.salton123.sf.appwechat.model.entity.kaiyan.VideoListBean;
import com.salton123.sf.mvp.presenter.BasePresenter;
import com.salton123.sf.mvp.view.BaseView;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/18 11:09
 * Time: 11:09
 * Description:
 */
public interface VideoFragmentContract {

    interface View extends BaseView {
        void showLoading();

        void dismissLoading();

        void showData(VideoListBean datalist);
        void showError(String errorMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(String date);
    }
}
