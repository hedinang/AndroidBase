package com.fashion.di.module;

import com.fashion.core.SessionManager;
import com.fashion.data.DataManager;
import com.fashion.data.DataManagerImp;
import com.fashion.data.db.DataBaseHelper;
import com.fashion.data.db.DataBaseHelperImp;
import com.fashion.data.local.SharePrefsHelper;
import com.fashion.data.local.SharedPrefsHelperImp;

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
