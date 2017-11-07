
package com.salton123.sf;

import android.app.Application;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

import net.wequick.small.Small;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/30 21:13
 * ModifyTime: 21:13
 * Description:
 */
public class SaltonApplication extends Application {

    public SaltonApplication() {
        Small.preSetUp(this);       //由于ContentProvider在onCreate之前被调用，为支持在插件中使用该组件，我们需要提前到构造方法来对之进行懒加载。
        Small.setBaseUri("http://salton123.com");
    }

    public static SaltonApplication mInstance;

    public static <T extends SaltonApplication> T getInstance() {
        return (T) mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
    }

    /**
     * 退出App
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
