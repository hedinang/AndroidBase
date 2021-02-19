package com.nanobookv2.ui.fragment.forgotpassword;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.ui.dialog.DialogMessage;
import com.nanobookv2.ui.view_model.AuthViewModel;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ConfirmNewPasswordFragment extends BaseFragment {

    private static final String CODE = "CODE";

    @BindView(R.id.edit_password)
    EditText edtPassword;

    @BindView(R.id.edit_password_confirm)
    EditText edtPasswordConfirm;

    @BindView(R.id.iv_hint_password)
    ImageView ivHintPassword;

    @BindView(R.id.iv_hint_confirm_password)
    ImageView ivHintConfirmPassword;

    AuthViewModel authViewModel;

    private String mCode;

    public static ConfirmNewPasswordFragment newInstance(String code) {
        ConfirmNewPasswordFragment fragment = new ConfirmNewPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CODE, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_confirm_new_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCode = getArguments().getString(CODE);
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

        authViewModel.isVerifyForgotPasswordSuccess.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                String successMessage = getString(R.string.message_change_password_successfully);
                DialogMessage dialogMessage = new DialogMessage(successMessage);
                dialogMessage.setOnOkClickListener(() -> mActivity.finish());
                dialogMessage.open(mActivity.getSupportFragmentManager());
            }
        });

        authViewModel.idMessage.observe(this, id -> {
            if (id != null && id != 0)
                new DialogMessage(getString(id)).open(mActivity.getSupportFragmentManager());
        });

        authViewModel.message.observe(this, message -> {
            if (message != null)
                new DialogMessage(message).open(mActivity.getSupportFragmentManager());
        });
    }

    @OnClick(R.id.iv_hint_password)
    void hintPasswordTap(){
        boolean isSelected = ivHintPassword.isSelected();
        ivHintPassword.setSelected(!isSelected);
        updateInputType(edtPassword, ivHintPassword.isSelected());
    }

    @OnClick(R.id.iv_hint_confirm_password)
    void hintConfirmPasswordTap(){
        boolean isSelected = ivHintConfirmPassword.isSelected();
        ivHintConfirmPassword.setSelected(!isSelected);
        updateInputType(edtPasswordConfirm, ivHintConfirmPassword.isSelected());
    }

    @OnClick(R.id.btn_change_password)
    void changePassword() {
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();
        authViewModel.verifyForgotPassword(mCode, password, passwordConfirm);
    }


    private void updateInputType(EditText edt, boolean isShowPassword){
        if (isShowPassword){
            edt.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            Typeface tf = edtPassword.getTypeface();
            edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt.setTypeface(tf);
        }
        if (edt.isFocused()){
            int length = edt.length();
            edt.setSelection(length);
        }
    }
}
