package com.fashion.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.login.LoginManager;
import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.core.ui.HandleViewModelBasic;
import com.fashion.data.model.auth.LoginDTO;
import com.fashion.ui.activity.ForgetPasswordActivity;
import com.fashion.ui.dialog.DialogErrorNetwork;
import com.fashion.ui.login.LoginFacebookAble;
import com.fashion.ui.login.LoginGoogleAble;
import com.fashion.ui.view_model.AuthViewModel;
import com.fashion.utils.Fragments;
import com.fashion.utils.Navigator;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

@SuppressLint("NonConstantResourceId")
public class LoginFragment extends BaseFragment implements LoginFacebookAble, LoginGoogleAble, HandleViewModelBasic {
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    AuthViewModel authViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
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
            if (isAuthenticated) Navigator.pushToMain(mActivity);
        });

        authViewModel.networkInvalid.observe(this, networkInvalid -> {
            if (networkInvalid != null && networkInvalid)
                new DialogErrorNetwork(this::loadData).open(mActivity.getSupportFragmentManager());
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

    @OnClick(R.id.btn_back)
    void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_do_login)
    void doLogin() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        LoginDTO.EmailRequest request = new LoginDTO.EmailRequest();
        request.email = email;
        request.password = password;
        authViewModel.login(request);
    }

    @OnClick(R.id.btn_goto_forget_password)
    void gotoForgetPassword() {
        Navigator.push(this.mActivity, ForgetPasswordActivity.class);
    }

    @OnClick(R.id.btn_goto_sign_up)
    void gotoSignUpOption() {
        SignUpOptionFragment fragment = Fragments.getByTag(mActivity.getSupportFragmentManager(), SignUpOptionFragment.class);
        if (fragment == null)
            Fragments.replace(
                    mActivity.getSupportFragmentManager(),
                    R.id.container_auth,
                    SignUpOptionFragment.newInstance(),
                    null,
                    true
            );
        else
            Fragments.popEntireBackStack(mActivity.getSupportFragmentManager());
    }

    @OnClick(R.id.btn_login_facebook)
    void doLoginFacebook() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("email"));
    }

    @OnClick(R.id.btn_login_google)
    void doLoginGoogle() {
        getSignInClient(mContext).signOut();
        Intent signInIntent = getSignInClient(mContext).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.btn_login_apple)
    void doLoginApple() {

    }

    @OnEditorAction(R.id.edit_password)
    boolean onActionDoneLogin(int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            doLogin();
            return true;
        }
        return false;
    }


}
