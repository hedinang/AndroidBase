package com.nanobookv2.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.ui.adapter.PagerAdapter;
import com.nanobookv2.ui.fragment.WelcomeFragment;
import com.nanobookv2.utils.Navigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

@SuppressLint("NonConstantResourceId")
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_circle_indicator)
    CircleIndicator circleIndicator;

    @BindView(R.id.view_pager_welcome)
    ViewPager viewPagerWelcome;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        activityComponent.inject(this);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        initStateViewPager();
    }

    private void initStateViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WelcomeFragment.newInstance(R.string.book_summary, R.string.book_summary_content, R.drawable.image_welcome_1));
        fragments.add(WelcomeFragment.newInstance(R.string.audiobook, R.string.audiobook_content, R.drawable.image_welcome_2));
        fragments.add(WelcomeFragment.newInstance(R.string.share, R.string.share_content, R.drawable.image_welcome_3));
        PagerAdapter pagerAdapter = new PagerAdapter(this, fragments);
        viewPagerWelcome.setAdapter(pagerAdapter);
        circleIndicator.setViewPager(viewPagerWelcome);
    }

    @OnClick(R.id.btn_sign_up)
    void gotoSignUp() {
        Bundle bundle = new Bundle();
        bundle.putInt(AuthActivity.KEY_TYPE, AuthActivity.FRAGMENT_SIGN_UP);
        Navigator.push(this, AuthActivity.class, bundle);
    }

    @OnClick(R.id.btn_login)
    void gotoLogin() {
        Bundle bundle = new Bundle();
        bundle.putInt(AuthActivity.KEY_TYPE, AuthActivity.FRAGMENT_LOGIN);
        Navigator.push(this, AuthActivity.class, bundle);
    }
}
