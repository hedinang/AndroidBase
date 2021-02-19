package com.nanobookv2.di.module;

import com.nanobookv2.core.config.DataSource;
import com.nanobookv2.di.qualifier.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    @DatabaseInfo
    static String providerDatabaseName() {
        return DataSource.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    static Integer providerDatabaseVersion() {
        return DataSource.DB_VERSION;
    }
}
