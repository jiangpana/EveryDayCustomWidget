package com.jansir.widget;

import android.util.Log;

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/29.
 */
public class LaunchTime {

    private static long startTime;
    private static String TAG = LaunchTime.class.getSimpleName();

    public static void startRecord() {
        startTime = System.currentTimeMillis();
    }

    public static void stopRecord() {
        long time = (System.currentTimeMillis() - startTime);
        Log.e(TAG, "stopRecord:应用的启动时间： " + time);
    }
}
