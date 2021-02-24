package com.fashion.ui.fragment.forgotpassword;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModelProvider;

import com.chaos.view.PinView;
import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.ui.view_model.AuthViewModel;
import com.fashion.utils.Fragments;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ConfirmCodeFragment extends BaseFragment {

    @BindView(R.id.pin_view_confirm_code)
    PinView pinView;

    AuthViewModel authViewModel;

    public static ConfirmCodeFragment newInstance() {
        return new ConfirmCodeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_confirm_code;
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

    @OnClick(R.id.btn_goto_change_password)
    void gotoChangePassword(){
        hideSoftKeyboard(mActivity);
        String code = pinView.getText().toString();
        if (authViewModel.isValidCode(code)){
            Fragments.add(
                    this.mActivity.getSupportFragmentManager(),
                    R.id.container_forget_password,
                    ConfirmNewPasswordFragment.newInstance(code),
                    null,
                    true
            );
        }
    }
}
