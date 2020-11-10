package com.wecare.android;

import android.app.Activity;
import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.Twitter;
import com.wecare.android.data.local.prefs.AppPreferencesHelper;
import com.wecare.android.di.component.DaggerAppComponent;
import com.wecare.android.utils.AppLogger;
import com.wecare.android.utils.ResponseTypeAdapterFactory;

import java.lang.reflect.Modifier;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

//import com.twitter.sdk.android.core.Twitter;


public class WeCareApplication extends Application implements HasActivityInjector {

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    AppPreferencesHelper appPreferencesHelper;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static WeCareApplication weCareApplication;

    public static synchronized WeCareApplication getInstance() {
        return weCareApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        weCareApplication = this;

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        if (BuildConfig.isSeekerFlavor)
            Twitter.initialize(this);

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(new ResponseTypeAdapterFactory());
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
//        builder.excludeFieldsWithoutExposeAnnotation();
        builder.serializeNulls();
        builder.setLenient();
        Gson gson = builder.create();

        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public AppPreferencesHelper getAppPreferencesHelper() {
        return appPreferencesHelper;
    }
}
