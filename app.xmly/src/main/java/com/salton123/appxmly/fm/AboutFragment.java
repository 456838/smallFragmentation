package com.salton123.appxmly.fm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.base.BaseSupportFragment;
import com.salton123.sf.libbase.widget.StatusTitleBar;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/31 11:46
 * ModifyTime: 11:46
 * Description:
 */
public class AboutFragment extends BaseSupportFragment{
    private TextView tv_thumbs_up ,tv_version;
    private StatusTitleBar titleBar;
    @Override
    public int GetLayout() {
        return R.layout.fm_about;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        tv_thumbs_up=f(R.id.tv_thumbs_up);
        tv_thumbs_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + _mActivity.getPackageName()));
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                _mActivity.startActivity(intent);
            }
        });
        tv_version=f(R.id.tv_version);
        String version = getAppVersionName(_mActivity);
        tv_version.setText("V "+version);
        titleBar = f(R.id.tv_titlebar);
        titleBar.setTitleText("关于", View.VISIBLE).setBackText("返回",View.VISIBLE).setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

    }

    @Override
    public void InitListener() {
        f(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setType("*/*");
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(_mActivity.getPackageResourcePath())));
                _mActivity.startActivity(intent);
            }
        });


    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String appVersionName = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名，versionCode同理
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }
}
