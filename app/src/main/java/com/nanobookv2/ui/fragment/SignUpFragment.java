package com.nanobookv2.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.login.LoginManager;
import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.data.model.auth.SignUpDTO;
import com.nanobookv2.ui.dialog.DialogErrorNetwork;
import com.nanobookv2.ui.dialog.DialogLoading;
import com.nanobookv2.ui.dialog.DialogMessage;
import com.nanobookv2.ui.login.LoginFacebookAble;
import com.nanobookv2.ui.login.LoginGoogleAble;
import com.nanobookv2.ui.view_model.AuthViewModel;
import com.nanobookv2.utils.Fragments;
import com.nanobookv2.utils.Navigator;

import java.util.Arrays;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

@SuppressLint("NonConstantResourceId")
public class SignUpFragment extends BaseFragment implements LoginFacebookAble, LoginGoogleAble, HandleViewModelBasic {

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.edit_password_confirm)
    EditText editPasswordConfirm;

    AuthViewModel authViewModel;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up;
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
        super.initStateUI();
        initFacebookSDK();
        handleViewModelBase(mActivity, authViewModel);
        authViewModel.isAuthenticated.observe(this, isAuthenticated -> {
            Navigator.pushToMain(this.mActivity);
        });

        authViewModel.networkInvalid.observe(this, networkInvalid -> {
            if (networkInvalid != null && networkInvalid)
                new DialogErrorNetwork(this::doSignUp).open(mActivity.getSupportFragmentManager());
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

    @OnClick(R.id.btn_sign_up_facebook)
    void gotoSignUpFacebook() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("email"));    }

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

    @OnClick(R.id.btn_sign_up)
    void doSignUp() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String passwordConfirm = editPasswordConfirm.getText().toString();
        SignUpDTO.EmailRequest request = new SignUpDTO.EmailRequest();
        request.email = email;
        request.password = password;
        request.passwordConfirm = passwordConfirm;
        authViewModel.signUp(request);
    }

    @OnClick(R.id.tv_service_rules)
    void gotoServiceRules() {
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
                    null,
                    true
            );
        else
            Fragments.popEntireBackStack(mActivity.getSupportFragmentManager());
    }

    @OnEditorAction(R.id.edit_password_confirm)
    boolean onActionDoneRegister(int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            return true;
        }
        return false;
    }

}
