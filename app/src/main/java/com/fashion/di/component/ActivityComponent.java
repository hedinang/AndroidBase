package com.fashion.di.component;

import com.fashion.di.module.NetworkModule;
import com.fashion.di.module.ViewModelModule;
import com.fashion.di.scope.ActivityScope;
import com.fashion.ui.activity.AuthActivity;
import com.fashion.ui.activity.ForgetPasswordActivity;
import com.fashion.ui.activity.MainActivity;
import com.fashion.ui.activity.SplashScreenActivity;
import com.fashion.ui.activity.WelcomeActivity;

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
