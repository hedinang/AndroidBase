package com.fashion.di.module;

import com.fashion.core.config.DataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    static String providerDatabaseName() {
        return DataSource.DB_NAME;
    }

    @Provides
    static Integer providerDatabaseVersion() {
        return DataSource.DB_VERSION;
    }


}
