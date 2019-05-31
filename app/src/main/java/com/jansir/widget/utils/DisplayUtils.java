package com.jansir.widget.utils;

import android.content.Context;

/**
 * author: jansir.
 * package: com.jansir.widget.utils.
 * date: 2019/5/28.
 */
public class DisplayUtils {

    public static int dp2px(Context context, int dpValue) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    public static int px2dp(Context context, int pxValue) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
    }
    public static int px2sp(Context context, int pxValue) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
