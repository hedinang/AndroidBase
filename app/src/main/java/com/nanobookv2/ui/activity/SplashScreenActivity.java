package com.nanobookv2.ui.activity;


import androidx.lifecycle.ViewModelProvider;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.ui.dialog.DialogErrorNetwork;
import com.nanobookv2.ui.dialog.DialogMessage;
import com.nanobookv2.ui.view_model.NanoViewModel;
import com.nanobookv2.ui.view_model.SplashViewModel;
import com.nanobookv2.utils.Navigator;

public class SplashScreenActivity extends BaseActivity implements HandleViewModelBasic {

    SplashViewModel splashViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        activityComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        splashViewModel = new ViewModelProvider(this, viewModelProvider).get(SplashViewModel.class);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        splashViewModel.isAuthenticated.observe(this, isAuthenticated -> {
            if (isAuthenticated == SplashViewModel.APPROVED)
                Navigator.pushToMain(this);
            else if (isAuthenticated == SplashViewModel.REJECTED)
                Navigator.pushToWelcome(this);
            else {
                System.out.println("Pending authentication .... ");
            }
        });
        splashViewModel.networkInvalid.observe(this, networkInvalid -> {
            if (networkInvalid != null && networkInvalid)
                new DialogErrorNetwork(new DialogErrorNetwork.TryClickListener() {
                    @Override
                    public void onTryAgain() {
                        if (splashViewModel.currentAction == SplashViewModel.ACTION_LOAD_ME)
                            loadData();
                    }

                    @Override
                    public void onCancel() {
                        SplashScreenActivity.this.finish();
                    }
                }).open(this.getSupportFragmentManager());
        });
        handleViewModelBase(this, splashViewModel);
    }

    @Override
    public void handleMessageId(BaseActivity context, NanoViewModel nanoViewModel) {
        splashViewModel.idMessage.observe(this, messageId -> {
            if (messageId != null)
                if (messageId == R.string.message_timeout) {
                    new DialogErrorNetwork(this::loadData).open(this.getSupportFragmentManager());
                } else {
                    new DialogMessage(getString(messageId)).open(this.getSupportFragmentManager());
                }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        splashViewModel.getMe();
    }
}
