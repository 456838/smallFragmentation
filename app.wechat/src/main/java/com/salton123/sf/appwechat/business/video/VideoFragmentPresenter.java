package com.salton123.sf.appwechat.business.video;


import com.salton123.sf.appwechat.model.api.OnlyOneApi;
import com.salton123.sf.appwechat.model.entity.kaiyan.VideoListBean;
import com.salton123.sf.mvp.presenter.RxPresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/18 11:09
 * Time: 11:09
 * Description:
 */
public class VideoFragmentPresenter extends RxPresenter<VideoFragmentContract.View> implements VideoFragmentContract.Presenter {

    @Override
    public void getData(String date) {
        mView.showLoading();
        Observable<VideoListBean> observable = OnlyOneApi.GetKyApiService().getVideoList(date);
        Disposable subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoListBean>() {
                    @Override
                    public void accept(VideoListBean videoListBean) throws Exception {
                        if (videoListBean != null) {
                            mView.showData(videoListBean);
                        } else {
                            mView.showError("data is null");
                        }
                        mView.dismissLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError("exception:" + throwable.getLocalizedMessage());
                        mView.dismissLoading();
                    }
                });

        addSubscrebe(subscription);
    }
}
