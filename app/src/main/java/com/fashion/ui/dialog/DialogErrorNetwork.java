package com.fashion.ui.dialog;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.fashion.R;
import com.fashion.core.ui.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DialogErrorNetwork extends BaseDialogFragment {

    @BindView(R.id.tv_message)
    TextView tvMessage;
    TryClickListener tryClickListener;
    public DialogErrorNetwork(TryClickListener tryClickListener){
        this.tryClickListener = tryClickListener;
    }
    @Override
    protected int getLayout() {
        return R.layout.dialog_error_network;
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        tvMessage.setText(R.string.message_network_invalid);
    }

    @OnClick(R.id.btn_try_again)
    void doTryAgain() {
        close();
        if (tryClickListener != null) tryClickListener.onTryAgain();
    }

    @OnClick(R.id.btn_cancel)
    void doCancel() {
        close();
        if (tryClickListener != null) tryClickListener.onCancel();

    }

    public interface TryClickListener {
        void onTryAgain();
        default void onCancel(){}
    }
}
