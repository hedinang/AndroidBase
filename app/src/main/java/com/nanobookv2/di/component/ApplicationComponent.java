package com.nanobookv2.di.component;

import android.content.Context;

import com.nanobookv2.NanoApplication;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.data.DataManager;
import com.nanobookv2.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {
        DataModule.class
})
public interface ApplicationComponent {
    Context getContext();
    SessionManager getSessionManager();
    DataManager getDataManager();
    void inject(NanoApplication application);
}
