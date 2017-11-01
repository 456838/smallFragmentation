package com.salton123.appxmly.fm;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.appxmly.model.ApiMethod;
import com.salton123.appxmly.model.ApiPair;
import com.salton123.base.BaseSupportSwipeBackFragment;
import com.salton123.callback.HttpResponseHandler;

import java.lang.reflect.Method;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 15:50
 * Time: 15:50
 * Description:
 */
public class ApiViewFragment extends BaseSupportSwipeBackFragment {
    private TextView tv_name, tv_content;

    @Override
    public int GetLayout() {
        return R.layout.fm_api_view;
    }

    private ApiPair pair;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        pair = (ApiPair) getArguments().getSerializable(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        tv_name = f(R.id.tv_name);
        tv_content = f(R.id.tv_content);
        tv_name.setText(pair.first);
        invokeMethod(pair.second);
    }

    @Override
    public void InitListener() {

    }

    public void invokeMethod(String methodName) {
        try {
            Class<?> clazz = ApiMethod.class;
            Method method = clazz.getMethod(methodName, HttpResponseHandler.class);
            method.invoke(clazz.newInstance(), new HttpResponseHandler<String>() {
                @Override
                public void onSuccess(String responseData) {
                    tv_content.setText(Html.fromHtml(responseData));
                    System.out.println(responseData);
                }

                @Override
                public void onFailure(String failedReason) {
                    tv_content.setText(failedReason);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
