package com.fashion.di.component;

import android.content.Context;

import com.fashion.NanoApplication;
import com.fashion.core.SessionManager;
import com.fashion.data.DataManager;
import com.fashion.di.module.DataModule;

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
