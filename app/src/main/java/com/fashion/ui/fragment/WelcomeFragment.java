package com.fashion.ui.fragment;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.fashion.R;
import com.fashion.core.ui.BaseFragment;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class WelcomeFragment extends BaseFragment {
    @BindView(R.id.image_welcome)
    ImageView imageWelcome;

    @BindView(R.id.tv_welcome_title)
    TextView tvWelcomeTitle;

    @BindView(R.id.tv_welcome_content)
    TextView tvWelcomeContent;

    private @StringRes
    int titleId;
    private @StringRes
    int contentId;
    private @DrawableRes
    int imageId;

    public static WelcomeFragment newInstance(@StringRes int title, @StringRes int content, @DrawableRes int image) {
        WelcomeFragment instance = new WelcomeFragment();
        instance.titleId = title;
        instance.contentId = content;
        instance.imageId = image;
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_welcome;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        tvWelcomeTitle.setText(titleId);
        tvWelcomeContent.setText(contentId);
        imageWelcome.setImageResource(imageId);
    }
}
