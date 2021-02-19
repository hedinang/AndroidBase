package com.nanobookv2.di.component;

import com.nanobookv2.di.module.NetworkModule;
import com.nanobookv2.di.module.ViewModelModule;
import com.nanobookv2.di.scope.ActivityScope;
import com.nanobookv2.ui.activity.AuthActivity;
import com.nanobookv2.ui.activity.ForgetPasswordActivity;
import com.nanobookv2.ui.activity.MainActivity;
import com.nanobookv2.ui.activity.SplashScreenActivity;
import com.nanobookv2.ui.activity.WelcomeActivity;

import dagger.Component;
@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ViewModelModule.class,
                NetworkModule.class,
        }
)
public interface ActivityComponent {
    void inject(SplashScreenActivity activity);
    void inject(WelcomeActivity activity);
    void inject(MainActivity activity);
    void inject(AuthActivity activity);
    void inject(ForgetPasswordActivity activity);

    @Component.Builder
    interface Builder {
        Builder applicationComponent(ApplicationComponent component);
        ActivityComponent build();
    }
}
