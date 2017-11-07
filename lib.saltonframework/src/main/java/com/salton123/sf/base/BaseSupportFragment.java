package com.salton123.sf.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.salton123.sf.util.StringUtils;
import com.salton123.sf.util.LogUtils;
import com.salton123.sf.util.ViewUtils;

import java.io.Serializable;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * User: 巫金生(newSalton@163.com)
 * Date: 2017/6/6 23:45
 * Description:
 * Updated:
 */
public abstract class BaseSupportFragment extends SupportFragment {

    public static <T extends Fragment> T newInstance(Class<T> clz) {
        Bundle bundle = new Bundle();
        T fragment = null;
        try {
            fragment = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ITEM, value);
        T fragment = null;
        try {
            fragment = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, Parcelable value) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ITEM, value);
        T fragment = null;
        try {
            fragment = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    protected static final String ARG_ITEM = "arg_item";

    public View mContentView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVariable(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = LayoutInflater.from(getActivity()).inflate(GetLayout(), null);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViewAndData();
        InitListener();
    }

    public abstract int GetLayout();

    public abstract void InitVariable(Bundle savedInstanceState);

    public abstract void InitViewAndData();

    public abstract void InitListener();


    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT f(@IdRes int id) {
        return ViewUtils.f(mContentView, id);

    }

    /**
     * 获取Activity
     *
     * @param <VT>
     * @return
     */
    protected <VT extends Activity> VT activity() {
        return (VT) _mActivity;
    }

    public void toast(String p_Msg) {
        if (StringUtils.isEmpty(p_Msg)) {
            return;
        }
        Toast.makeText(_mActivity, p_Msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @return 布局解析器
     */
    public LayoutInflater inflater() {
        LayoutInflater _LayoutInflater = LayoutInflater.from(_mActivity);
        return _LayoutInflater;
    }

    public void log(String p_Msg) {
        LogUtils.d(p_Msg);
    }

    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.dispose();
    }

    /**
     * 根据类打开Activity
     *
     * @param pClass
     */
    public void OpenActivity(Class<?> pClass) {
        OpenActivity(pClass, null);
    }

    /**
     * 带有参数的根据类打开Activity
     *
     * @param pClass
     * @param pBundle 封装的参数
     */
    protected void OpenActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(context(), pClass);
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

    public Context context() {
        return _mActivity;
    }
}
