package com.salton123.appxmly.wrapper;

import android.widget.Toast;

import com.salton123.base.ApplicationBase;
import com.salton123.mvp.util.RxBus;
import com.salton123.util.LogUtils;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 11:37
 * Time: 11:37
 * Description:
 */
public class XmPlayerStatusAdapter implements IXmPlayerStatusListener {
    public XmPlayerStatusAdapter(){
        RxBus.getDefault().post(this);
    }
    @Override
    public void onPlayStart() {
    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curMode) {

    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int currPos, int duration) {

    }

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtils.e(e);
        Toast.makeText(ApplicationBase.getInstance(),"ex="+e.getMessage(),Toast.LENGTH_SHORT).show();
        return false;
    }
}
