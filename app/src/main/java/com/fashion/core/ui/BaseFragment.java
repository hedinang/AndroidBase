package com.fashion.core.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fashion.NanoApplication;
import com.fashion.di.component.DaggerFragmentComponent;
import com.fashion.di.component.FragmentComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected BaseActivity mActivity;
    protected FragmentComponent fragmentComponent;
    protected View root;
    protected abstract @LayoutRes
    int getLayout();

    @Inject
    protected ViewModelProvider.Factory viewModelProvider;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getContext();
        initComponent();
        initViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, root);
        setupUI(root.getRootView());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateUI();
        loadData();
    }

    protected void initComponent() {
        fragmentComponent = DaggerFragmentComponent
                .builder()
                .applicationComponent(NanoApplication.getApplicationComponent())
                .build();
    }

    protected void initViewModel() {

    }

    protected void initStateUI() {

    }

    protected void loadData(){

    }

    protected void onBackPressed(){
        mActivity.onBackPressed();
    }

    public static void hideSoftKeyboard(Activity activity) {
        Handler handler = new Handler();
        handler.postDelayed(()->{
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.findViewById(android.R.id.content).getWindowToken(), 0);
        }, 200);
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard(mActivity);
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

}
