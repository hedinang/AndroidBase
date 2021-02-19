package com.nanobookv2.ui.activity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.ui.fragment.forgotpassword.ConfirmEmailFragment;
import com.nanobookv2.ui.view_model.AuthViewModel;
import com.nanobookv2.utils.Fragments;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;

    AuthViewModel authViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_password;
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

        tvTitle.setText(getString(R.string.password_forget));

        Fragments.replace(
                this.getSupportFragmentManager(),
                R.id.container_forget_password,
                ConfirmEmailFragment.newInstance(),
                null,
                false
        );
    }


    @OnClick(R.id.btn_back)
    void onBack(){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authViewModel.dispose();
    }
}