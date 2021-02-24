package com.fashion.di.component;

import com.fashion.di.module.NetworkModule;
import com.fashion.di.module.ViewModelModule;
import com.fashion.di.scope.ActivityScope;
import com.fashion.ui.fragment.AccountFragment;
import com.fashion.ui.fragment.BookMoreFragment;
import com.fashion.ui.fragment.CampaignDetailFragment;
import com.fashion.ui.fragment.CategoryDetailFragment;
import com.fashion.ui.fragment.SearchFragment;
import com.fashion.ui.fragment.forgotpassword.ConfirmCodeFragment;
import com.fashion.ui.fragment.forgotpassword.ConfirmEmailFragment;
import com.fashion.ui.fragment.forgotpassword.ConfirmNewPasswordFragment;
import com.fashion.ui.fragment.HomeFragment;
import com.fashion.ui.fragment.LibraryFragment;
import com.fashion.ui.fragment.LoginFragment;
import com.fashion.ui.fragment.OverViewBookFragment;
import com.fashion.ui.fragment.ReadingFragment;
import com.fashion.ui.fragment.SignUpFragment;
import com.fashion.ui.fragment.SignUpOptionFragment;
import com.fashion.ui.fragment.WelcomeFragment;

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
