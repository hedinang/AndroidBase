package com.fashion.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.fashion.core.config.SharePrefConfig;
import com.fashion.di.qualifier.SharePrefsInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class SharePrefsModule {
    private final Context context;

    public SharePrefsModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providerContext() {
        return context;
    }

    @Provides
    @SharePrefsInfo
    String providerSharePrefsName() {
        return SharePrefConfig.SHARE_PREF_NAME;
    }

    @Provides
    SharedPreferences provideSharedPreference(Context context, @SharePrefsInfo String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
