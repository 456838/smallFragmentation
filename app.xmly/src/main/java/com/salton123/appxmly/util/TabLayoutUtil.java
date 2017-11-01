package com.salton123.appxmly.util;

import android.support.design.widget.TabLayout;
import android.view.View;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/7/1 15:40
 * Time: 15:40
 * Description:
 */
public class TabLayoutUtil {
    /**
     * 根据Tab合起来的长度动态修改tab的模式
     *
     * @param tabLayout TabLayout
     */
    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabTotalWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0);
            tabTotalWidth += view.getMeasuredWidth();
        }
        if (tabTotalWidth <= MeasureUtil.getScreenSize(tabLayout.getContext()).x) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }
}
