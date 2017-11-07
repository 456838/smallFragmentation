package com.salton123.sf.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.salton123.sf.util.LogUtils;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/14 19:18
 * Time: 19:18
 * Description:
 */
public abstract class BaseActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVariable();
        setContentView(GetLayout());
        InitViewAndData();
        InitListener();
    }

    public abstract int GetLayout();

    public abstract void InitVariable();

    public abstract void InitViewAndData();

    public abstract void InitListener();

    public void toast(String p_Msg) {
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
