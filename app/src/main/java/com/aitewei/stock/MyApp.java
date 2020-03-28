package com.aitewei.stock;

import android.app.Application;

import com.tencent.bugly.Bugly;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "a8817eb57d", false);
    }
}
