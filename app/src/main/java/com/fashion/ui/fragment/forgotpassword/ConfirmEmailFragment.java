package com.fashion.ui.fragment.forgotpassword;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.core.ui.HandleViewModelBasic;
import com.fashion.ui.view_model.AuthViewModel;
import com.fashion.utils.Fragments;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ConfirmEmailFragment extends BaseFragment implements HandleViewModelBasic {

    @BindView(R.id.edit_confirm_email)
    EditText edtEmail;

    @BindView(R.id.tv_error_email)
    TextView tvError;

    AuthViewModel authViewModel;

    public static ConfirmEmailFragment newInstance() {
        return new ConfirmEmailFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_confirm_email;
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
        handleViewModelBase(mActivity, authViewModel);
        authViewModel.isConfirmEmailSuccess.observe(getViewLifecycleOwner(), isConfirmEmailSuccess ->{
            if (isConfirmEmailSuccess){
                Fragments.add(
                        this.mActivity.getSupportFragmentManager(),
                        R.id.container_forget_password,
                        ConfirmCodeFragment.newInstance(),
                        null,
                        true
                );
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity() != null){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    @OnClick(R.id.btn_goto_confirm_code)
    void gotoCodeConfirm(){
        String email = edtEmail.getText().toString();
        if (authViewModel.isValidEmail(email)){
            tvError.setVisibility(View.INVISIBLE);
            authViewModel.confirmEmail(email);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
