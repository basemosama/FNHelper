package com.basemosama.fnhelper.utility;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MyApplication extends Application {
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
