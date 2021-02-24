package com.fashion.core.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fashion.NanoApplication;
import com.fashion.R;
import com.fashion.di.component.ActivityComponent;
import com.fashion.di.component.DaggerActivityComponent;
import com.fashion.ui.broadcast.NetworkConnectReceiver;
import com.fashion.ui.dialog.DialogLoading;

import javax.inject.Inject;

import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent activityComponent;
    protected NetworkConnectReceiver networkConnectReceiver = new NetworkConnectReceiver();
    public DialogLoading dialogLoading;
    protected abstract @LayoutRes
    int getLayout();

    @Inject
    protected ViewModelProvider.Factory viewModelProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initComponent();
        initViewModel();
        ButterKnife.bind(this);
        View root = ((FrameLayout) getWindow().getDecorView().getRootView()).getChildAt(0);
        setupUI(root);
        initStateUI();
        loadData();
        networkConnectReceiver.setNetworkConnectionListener(getNetworkListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(networkConnectReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkConnectReceiver);
    }

    protected void initComponent() {
        activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent(NanoApplication.getApplicationComponent())
                .build();
    }

    protected void initViewModel() {

    }

    protected void initStateUI() {

    }

    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static void hideSoftKeyboard(Activity activity) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.findViewById(android.R.id.content).getWindowToken(), 0);
        }, 200);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard(BaseActivity.this);
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    protected NetworkConnectReceiver.NetworkConnectionListener getNetworkListener(){
        return null;
    }
}
