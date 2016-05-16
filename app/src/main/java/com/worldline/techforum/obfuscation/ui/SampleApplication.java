package com.worldline.techforum.obfuscation.ui;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Francois Lolom on 16/05/2016.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.uprootAll();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
