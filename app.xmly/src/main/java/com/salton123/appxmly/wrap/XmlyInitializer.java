package com.salton123.appxmly.wrap;

import android.app.Application;
import android.util.SparseArray;

import com.salton123.appxmly.XmConfig;
import com.salton123.util.log.MLog;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.http.RequestParams;
import com.ximalaya.ting.android.sdkdownloader.http.app.RequestTracker;
import com.ximalaya.ting.android.sdkdownloader.http.request.UriRequest;

import java.io.File;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/13 21:55
 * ModifyTime: 21:55
 * Description:单例模式
 */
public class XmlyInitializer {
    private static final String TAG = "XmlyInitializer";
    private XmDownloadManager downloadManager;
    private CommonRequest commonRequest;
    private Application sApplication;
    private XmPlayerManager playerManager;
    private String defaultFilePath = XmConfig.XM_FILE_PATH + "mp3" + File.separator;

    private XmlyInitializer() {
    }

    private static XmlyInitializer INSTANCE;

    /**
     * DCL
     *
     * @return
     */
    public static XmlyInitializer getInstance() {
        if (INSTANCE == null) {
            synchronized (XmlyInitializer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XmlyInitializer();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 务必保证该方法会执行
     *
     * @param sApplication
     * @return
     */
    public XmlyInitializer attch(Application sApplication) {
        this.sApplication = sApplication;
        iXmPlayerStatusListenerArray = new SparseArray<>();
        iXmAdsStatusListenerArray = new SparseArray<>();
        return this;
    }

    /**
     * 初始化服务
     */
    public void init() {
        if (sApplication == null) {
            throw new ExceptionInInitializerError("请执行attach方法！");
        }
        commonRequest = CommonRequest.getInstanse();
        commonRequest.init(sApplication, XmConfig.APP_SECRET);
        getDownloadManager();

    }

    private SparseArray<IXmPlayerStatusListener> iXmPlayerStatusListenerArray;
    private SparseArray<IXmAdsStatusListener> iXmAdsStatusListenerArray;

    public <T> XmlyInitializer notify(Class<T> targetClass, IXmPlayerStatusListener iXmPlayerStatusListener, IXmAdsStatusListener iXmAdsStatusListener) {
        iXmPlayerStatusListenerArray.put(iXmPlayerStatusListener.hashCode(), iXmPlayerStatusListener);
        iXmAdsStatusListenerArray.put(iXmAdsStatusListener.hashCode(), iXmAdsStatusListener);
        if (sApplication == null) {
            throw new ExceptionInInitializerError("请执行attach方法！");
        }
        playerManager = XmPlayerManager.getInstance(sApplication);
        // Notification mNotification = XmNotificationCreater.getInstanse(sApplication).initNotification(sApplication, targetClass);
        // playerManager.init((int) System.currentTimeMillis(), mNotification);
        playerManager.init();
        playerManager.addPlayerStatusListener(iXmPlayerStatusListener);
        playerManager.addAdsStatusListener(iXmAdsStatusListener);
        playerManager.addOnConnectedListerner(new XmPlayerManager.IConnectListener() {
            @Override
            public void onConnected() {
                playerManager.removeOnConnectedListerner(this);
                playerManager.setPlayMode(XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP);
//                mPlayerManager.setPlayMode(XmPlayListControl.PlayMode.PLAY_MODEL_LIST);
                // Toast.makeText(sApplication, "播放器初始化成功", Toast.LENGTH_SHORT).show();
            }
        });
        return this;
    }

    public void addPlayerStatusListener(IXmPlayerStatusListener iXmPlayerStatusListener) {
        if (playerManager == null) {
            throw new ExceptionInInitializerError("请执行notify方法！");
        }
        if (iXmPlayerStatusListenerArray!=null){
            iXmPlayerStatusListenerArray.put(iXmPlayerStatusListener.hashCode(), iXmPlayerStatusListener);
        }
        playerManager.addPlayerStatusListener(iXmPlayerStatusListener);
    }

    public void removePlayerStatusListener(IXmPlayerStatusListener iXmPlayerStatusListener) {
        playerManager.removePlayerStatusListener(iXmPlayerStatusListener);
    }

    public XmPlayerManager getPlayerManager(){
        return playerManager;
    }

    /**
     * // 此代码表示播放时会去监测下是否已经下载
     *
     * @return
     */
    public XmlyInitializer businessHandle() {
        if (sApplication == null) {
            throw new ExceptionInInitializerError("请执行attach方法！");
        }
        // 此代码表示播放时会去监测下是否已经下载
        XmPlayerManager.getInstance(sApplication).setCommonBusinessHandle(getDownloadManager());
        return this;
    }


    /**
     * 释放
     */
    public void release() {
        for (int i = 0; i < iXmPlayerStatusListenerArray.size(); i++) {
            removePlayerStatusListener(iXmPlayerStatusListenerArray.valueAt(i));
        }
        for (int i = 0; i < iXmAdsStatusListenerArray.size(); i++) {
            XmPlayerManager.getInstance(sApplication).removeAdsStatusListener(iXmAdsStatusListenerArray.valueAt(i));
        }
        XmPlayerManager.release();
        CommonRequest.release();
    }

    /**
     * 获取下载管理器
     *
     * @return
     */
    public XmDownloadManager getDownloadManager() {
        if (downloadManager == null) {
            defaultDownloadManager();
            downloadManager = XmDownloadManager.getInstance();
        }
        return downloadManager;
    }

    /**
     * 定制下载管理器
     *
     * @param downloadManager
     * @return
     */
    public XmlyInitializer customDonwloadManager(XmDownloadManager downloadManager) {
        this.downloadManager = downloadManager;
        return this;
    }

    /**
     * 默认下载管理器
     *
     * @return
     */
    public XmlyInitializer defaultDownloadManager() {
        if (sApplication == null) {
            throw new ExceptionInInitializerError("请执行attach方法！");
        }
        this.downloadManager = XmDownloadManager.Builder(sApplication)
                .maxDownloadThread(1)            // 最大的下载个数 默认为1 最大为3
                .maxSpaceSize(Long.MAX_VALUE)    // 设置下载文件占用磁盘空间最大值，单位字节。不设置没有限制
                .connectionTimeOut(15000)        // 下载时连接超时的时间 ,单位毫秒 默认 30000
                .readTimeOut(15000)                // 下载时读取的超时时间 ,单位毫秒 默认 30000
                .fifo(false)                    // 等待队列的是否优先执行先加入的任务. false表示后添加的先执行(不会改变当前正在下载的音频的状态) 默认为true
                .maxRetryCount(3)                // 出错时重试的次数 默认2次
                .progressCallBackMaxTimeSpan(1000)//  进度条progress 更新的频率 默认是800
                .requestTracker(requestTracker)    // 日志 可以打印下载信息
                .savePath(defaultFilePath)    // 保存的地址 会检查这个地址是否有效
                .create();
        return this;
    }

    private RequestTracker requestTracker = new RequestTracker() {
        @Override
        public void onWaiting(RequestParams params) {
            MLog.info(TAG, "onWaiting " + params);
        }

        @Override
        public void onStart(RequestParams params) {
            MLog.info(TAG, "onStart " + params);
        }

        @Override
        public void onRequestCreated(UriRequest request) {
            MLog.info(TAG, "onRequestCreated " + request);
        }

        @Override
        public void onSuccess(UriRequest request, Object result) {
            MLog.info(TAG, "onSuccess " + request + "   result = " + result);
        }

        @Override
        public void onRemoved(UriRequest request) {
            MLog.info(TAG, "onRemoved " + request);
        }

        @Override
        public void onCancelled(UriRequest request) {
            MLog.info(TAG, "onCanclelled " + request);
        }

        @Override
        public void onError(UriRequest request, Throwable ex, boolean isCallbackError) {
            MLog.info(TAG, "onError " + request + "   ex = " + ex + "   isCallbackError = " + isCallbackError);
        }

        @Override
        public void onFinished(UriRequest request) {
            MLog.info(TAG, "onFinished " + request);
        }
    };


}
