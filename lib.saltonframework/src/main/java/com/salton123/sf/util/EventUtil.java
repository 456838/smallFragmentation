package com.salton123.sf.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cuieney on 16/9/8.
 */
public class EventUtil {

    public static void register(Object context) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }

    public static void unregister(Object context) {
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(object);
    }
}
