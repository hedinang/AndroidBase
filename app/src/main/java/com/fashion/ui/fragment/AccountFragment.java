package com.fashion.ui.fragment;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModelProvider;

import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.ui.view_model.AuthViewModel;

@SuppressLint("NonConstantResourceId")
public class AccountFragment extends BaseFragment {
    AuthViewModel authViewModel;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_account;
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
    }

}
