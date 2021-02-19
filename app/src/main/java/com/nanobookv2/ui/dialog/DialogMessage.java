package com.nanobookv2.ui.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DialogMessage extends BaseDialogFragment {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    private String message;
    private OnOkClickedListener mListener;

    public DialogMessage(String message){
        this.message = message;
    }
    @Override
    protected int getLayout() {
        return R.layout.dialog_message;
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        tvMessage.setText(message);
    }

    @OnClick(R.id.btn_ok)
    void doOk(){
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null){
            mListener.onOkClicked();
        }
    }

    public interface OnOkClickedListener{
        void onOkClicked();
    }

    public void setOnOkClickListener(OnOkClickedListener listener){
        mListener = listener;
    }
}
