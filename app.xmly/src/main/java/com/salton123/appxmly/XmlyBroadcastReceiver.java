package com.salton123.appxmly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/9 22:11
 * ModifyTime: 22:11
 * Description:
 */
public class XmlyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"onReceive",Toast.LENGTH_SHORT).show();
    }
}
