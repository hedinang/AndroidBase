package com.nanobookv2.di.module;

import com.nanobookv2.core.SessionManager;
import com.nanobookv2.data.DataManager;
import com.nanobookv2.data.DataManagerImp;
import com.nanobookv2.data.db.DataBaseHelper;
import com.nanobookv2.data.db.DataBaseHelperImp;
import com.nanobookv2.data.local.SharePrefsHelper;
import com.nanobookv2.data.local.SharedPrefsHelperImp;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        DataBaseModule.class,
        SharePrefsModule.class,
})
public abstract class DataModule {

    @Binds
    abstract SharePrefsHelper bindSharePrefsHelper(SharedPrefsHelperImp sharedPrefsHelperImp);

    @Binds
    abstract DataBaseHelper bindDataBaseHelper(DataBaseHelperImp dataBaseHelperImp);

    @Binds
    abstract DataManager bindDataManager(DataManagerImp dataManagerImp);

    @Provides
    @Singleton
    static SessionManager providerSessionManager(){
        return new SessionManager();
    }

}
