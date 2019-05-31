package com.jansir.widget;

import android.app.Application;
import android.content.Context;

import com.jansir.widget.LaunchTime;

/**
 * author: jansir.
 * package: com.jansir.
 * date: 2019/5/29.
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchTime.startRecord();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
