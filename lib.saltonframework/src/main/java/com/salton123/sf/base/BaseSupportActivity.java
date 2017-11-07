package com.salton123.sf.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.salton123.sf.util.StringUtils;
import com.salton123.sf.util.LogUtils;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/6/6.
 */

public abstract class BaseSupportActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVariable(savedInstanceState);
        setContentView(GetLayout());
        InitViewAndData();
        InitListener();
    }

    public abstract int GetLayout();

    public abstract void InitVariable(Bundle savedInstanceState);

    public abstract void InitViewAndData();

    public abstract void InitListener();

    public void toast(String p_Msg) {
        Log.d("toast",p_Msg);
        if (StringUtils.isEmpty(p_Msg)) {
            return;
        }
        Toast.makeText(this, p_Msg, Toast.LENGTH_SHORT).show();

    }

    public Context context() {
        return getApplicationContext();
    }

    /**
     * @return 布局解析器
     */
    public LayoutInflater inflater() {
        LayoutInflater _LayoutInflater = LayoutInflater.from(this);
        return _LayoutInflater;
    }

    public void log(String p_Msg) {
        LogUtils.d(p_Msg);
    }



    /**
     * 查找View
     *
     * @param resId 控件id
     * @return
     */
    public <T extends View> T f(@IdRes int resId) {
        return (T) findViewById(resId);
    }
    /**
     * 带有参数的根据类打开Activity
     *
     * @param pClass
     * @param pBundle 封装的参数
     */
    public void OpenActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    public void OpenActivityForResult(Class<?> pClass, int pRequestCode) {
        OpenActivityForResult(pClass, pRequestCode, null);
    }

    /**
     * 带有参数的根据类打开Activity
     *
     * @param pClass
     * @param pBundle 封装的参数
     */
    public void OpenActivityForResult(Class<?> pClass, int pRequestCode, Bundle pBundle) {
        Intent intent = new Intent(context(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, pRequestCode);
    }



}
