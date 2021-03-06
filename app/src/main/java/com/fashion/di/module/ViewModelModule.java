package com.fashion.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fashion.di.ViewModelKey;
import com.fashion.ui.view_model.AuthViewModel;
import com.fashion.ui.view_model.BookMarkViewModel;
import com.fashion.ui.view_model.CampaignViewModel;
import com.fashion.ui.view_model.ChapterBookViewModel;
import com.fashion.ui.view_model.HomeViewModel;
import com.fashion.ui.view_model.MainViewModel;
import com.fashion.ui.view_model.ListBookViewModel;
import com.fashion.ui.view_model.SettingViewModel;
import com.fashion.ui.view_model.SplashViewModel;
import com.fashion.ui.view_model.ViewModelFactory;
import com.fashion.ui.view_model.WelcomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel.class)
    abstract ViewModel bindWelcomeViewModel(WelcomeViewModel welcomeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListBookViewModel.class)
    abstract ViewModel bindMoreBookViewModel(ListBookViewModel listBookViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChapterBookViewModel.class)
    abstract ViewModel bindChapterBookViewModel(ChapterBookViewModel chapterBookViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CampaignViewModel.class)
    abstract ViewModel bindCampaignViewModel(CampaignViewModel campaignViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BookMarkViewModel.class)
    abstract ViewModel bindBookMarkViewModel(BookMarkViewModel bookMarkViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel.class)
    abstract ViewModel bindSettingViewModel(SettingViewModel settingViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}