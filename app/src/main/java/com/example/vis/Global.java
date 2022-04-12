package com.example.vis;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class Global extends Application {
    private static Global sInstance;


    public static Global globalContext() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}