package com.nanobookv2.ui.activity;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModelProvider;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.ui.fragment.LoginFragment;
import com.nanobookv2.ui.fragment.SignUpOptionFragment;
import com.nanobookv2.ui.view_model.AuthViewModel;
import com.nanobookv2.utils.Fragments;

@SuppressLint("NonConstantResourceId")
public class AuthActivity extends BaseActivity {
    public static final String KEY_TYPE = "KEY_TYPE";
    public static final int FRAGMENT_SIGN_UP = 1;
    public static final int FRAGMENT_LOGIN = 2;
    AuthViewModel authViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_auth;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        activityComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        authViewModel = new ViewModelProvider(this, viewModelProvider).get(AuthViewModel.class);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        BaseFragment fragment = null;
        int type = getIntent().getExtras().getInt(KEY_TYPE);
        switch (type) {
            case FRAGMENT_SIGN_UP:
                fragment = SignUpOptionFragment.newInstance();
                break;
            case FRAGMENT_LOGIN:
                fragment = LoginFragment.newInstance();
                break;
        }
        if (fragment != null)
            Fragments.replace(
                    this.getSupportFragmentManager(),
                    R.id.container_auth,
                    fragment,
                    null
            );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authViewModel.dispose();
    }
}