package com.fashion;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.fashion.core.SessionManager;
import com.fashion.core.config.SharePrefConfig;
import com.fashion.data.DataManager;
import com.fashion.di.component.ApplicationComponent;
import com.fashion.di.component.DaggerApplicationComponent;
import com.fashion.di.module.SharePrefsModule;

import java.util.Locale;

import javax.inject.Inject;

import static com.fashion.core.config.SharePrefConfig.KEY_DEVICE_ID;


public class NanoApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    @Inject
    DataManager dataManager;
    @Inject
    SessionManager sessionManager;
    private static NanoApplication instance;
    ApplicationComponent applicationComponent;

    public static NanoApplication getInstance() {
        return instance;
    }

    public static ApplicationComponent getApplicationComponent() {
        return instance.applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .sharePrefsModule(new SharePrefsModule(this))
                .build();
        applicationComponent.inject(this);
        createSessionApp();
        registerActivityLifecycleCallbacks(this);
    }

    @SuppressLint("HardwareIds")
    private void createSessionApp() {
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        sessionManager.deviceId = "1";
        sessionManager.language = Locale.getDefault().getLanguage();
        sessionManager.accessToken = dataManager.get(SharePrefConfig.KEY_TOKEN, "");
        dataManager.put(KEY_DEVICE_ID, deviceId);
    }



    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }
}
