package com.fashion.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.login.LoginManager;
import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.core.ui.HandleViewModelBasic;
import com.fashion.ui.login.LoginFacebookAble;
import com.fashion.ui.login.LoginGoogleAble;
import com.fashion.ui.view_model.AuthViewModel;
import com.fashion.utils.Fragments;
import com.fashion.utils.Navigator;

import java.util.Collections;

import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class SignUpOptionFragment extends BaseFragment implements LoginFacebookAble, LoginGoogleAble, HandleViewModelBasic {
    AuthViewModel authViewModel;

    public static SignUpOptionFragment newInstance() {
        return new SignUpOptionFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up_option;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        authViewModel = new ViewModelProvider(this, viewModelProvider).get(AuthViewModel.class);
    }

    @Override
    protected void initStateUI() {
        initFacebookSDK();
        super.initStateUI();
        handleViewModelBase(mActivity, authViewModel);
        authViewModel.isAuthenticated.observe(this, isAuthenticated -> {
            Navigator.pushToMain(this.mActivity);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            authViewModel.handleResultAuthGoogle(LoginGoogleAble.SIGN_UP, data);
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        authViewModel.dispose();
        super.onDestroy();
    }

    @OnClick(R.id.btn_sign_up_email)
    void gotoSignUpEmail() {
        Fragments.replace(
                mActivity.getSupportFragmentManager(),
                R.id.container_auth,
                SignUpFragment.newInstance(),
                null, true
        );
    }

    @OnClick(R.id.btn_sign_up_facebook)
    void gotoSignUpFacebook() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("email"));
    }

    @OnClick(R.id.btn_sign_up_google)
    void gotoSignUpGoogle() {
        getSignInClient(mContext).signOut();
        Intent signInIntent = getSignInClient(mContext).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @OnClick(R.id.btn_sign_up_apple)
    void gotoSignUpApple() {

    }

    @OnClick(R.id.btn_back)
    void goBack() {
        onBackPressed();
    }


    @OnClick(R.id.tv_service_rules)
    void gotoServiceRule() {
        String url = getString(R.string.service_rules_link);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @OnClick(R.id.btn_goto_login)
    void gotoLogin() {
        LoginFragment fragment = Fragments.getByTag(mActivity.getSupportFragmentManager(), LoginFragment.class);
        if (fragment == null)
            Fragments.replace(
                    mActivity.getSupportFragmentManager(),
                    R.id.container_auth,
                    LoginFragment.newInstance(),
                    null, true
            );
        else
            Fragments.popEntireBackStack(mActivity.getSupportFragmentManager());
    }
}
