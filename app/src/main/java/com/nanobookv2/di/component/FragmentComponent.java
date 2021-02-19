package com.nanobookv2.di.component;

import com.nanobookv2.di.module.NetworkModule;
import com.nanobookv2.di.module.ViewModelModule;
import com.nanobookv2.di.scope.ActivityScope;
import com.nanobookv2.ui.fragment.AccountFragment;
import com.nanobookv2.ui.fragment.BookMoreFragment;
import com.nanobookv2.ui.fragment.CampaignDetailFragment;
import com.nanobookv2.ui.fragment.CategoryDetailFragment;
import com.nanobookv2.ui.fragment.SearchFragment;
import com.nanobookv2.ui.fragment.forgotpassword.ConfirmCodeFragment;
import com.nanobookv2.ui.fragment.forgotpassword.ConfirmEmailFragment;
import com.nanobookv2.ui.fragment.forgotpassword.ConfirmNewPasswordFragment;
import com.nanobookv2.ui.fragment.HomeFragment;
import com.nanobookv2.ui.fragment.LibraryFragment;
import com.nanobookv2.ui.fragment.LoginFragment;
import com.nanobookv2.ui.fragment.OverViewBookFragment;
import com.nanobookv2.ui.fragment.ReadingFragment;
import com.nanobookv2.ui.fragment.SignUpFragment;
import com.nanobookv2.ui.fragment.SignUpOptionFragment;
import com.nanobookv2.ui.fragment.WelcomeFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ViewModelModule.class,
                NetworkModule.class
        }
)
public interface FragmentComponent {
    void inject(WelcomeFragment fragment);
    void inject(SignUpOptionFragment fragment);
    void inject(SignUpFragment fragment);
    void inject(LoginFragment fragment);
    /*main*/
    void inject(HomeFragment fragment);
    void inject(LibraryFragment fragment);
    void inject(AccountFragment fragment);

    /*forget password*/
    void inject(ConfirmEmailFragment fragment);
    void inject(ConfirmCodeFragment fragment);
    void inject(ConfirmNewPasswordFragment fragment);

    void inject(CampaignDetailFragment fragment);
    void inject(BookMoreFragment fragment);
    void inject(SearchFragment fragment);
    void inject(OverViewBookFragment fragment);
    void inject(CategoryDetailFragment fragment);
    void inject(ReadingFragment fragment);

    @Component.Builder
    interface Builder {
        Builder applicationComponent(ApplicationComponent component);
        FragmentComponent build();
    }
}
