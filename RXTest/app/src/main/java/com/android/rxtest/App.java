package com.android.rxtest;

import android.app.Application;
import android.content.Context;

/**
 * Created by gang.an on 2017/10/30.
 */

public class App extends Application{
    private static App app;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);
        app = this;
    }

    public static App getInstance() {
        return app;
    }

}
