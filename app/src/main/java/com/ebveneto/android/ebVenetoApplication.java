package com.ebveneto.android;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Created by Mayur on 10-11-2016.
 */
public class ebVenetoApplication extends MultiDexApplication {
    private static ebVenetoApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static ebVenetoApplication getInstance() {
        return mInstance;
    }

}
