
package com.salton123.sf;

import android.app.Application;

import net.wequick.small.Small;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/30 21:13
 * ModifyTime: 21:13
 * Description:
 */
public class SaltonApplication extends Application {

    public SaltonApplication(){
        Small.preSetUp(this);       //由于ContentProvider在onCreate之前被调用，为支持在插件中使用该组件，我们需要提前到构造方法来对之进行懒加载。
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
