package com.nanobookv2.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.ui.adapter.PagerAdapter;
import com.nanobookv2.ui.broadcast.NetworkConnectReceiver;
import com.nanobookv2.ui.fragment.AccountFragment;
import com.nanobookv2.ui.fragment.HomeFragment;
import com.nanobookv2.ui.fragment.LibraryFragment;
import com.nanobookv2.ui.view_model.MainViewModel;
import com.nanobookv2.utils.Navigator;
import com.nanobookv2.widget.ViewPagerSwipe;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HandleViewModelBasic {
    private static final int PAGE_HOME = 0;
    private static final int PAGE_LIBRARY = 1;
    private static final int PAGE_ACCOUNT = 2;
    private static final int OFF_PAGE_LIMIT = 3;
    @BindView(R.id.view_pager_main)
    ViewPagerSwipe viewPagerMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;
    MainViewModel mainViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        activityComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        mainViewModel = new ViewModelProvider(this, viewModelProvider).get(MainViewModel.class);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        /*bottom menu */
        bottomMenu.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        /*page menu*/
        PagerAdapter adapter = new PagerAdapter(this);
        adapter.addFragment(HomeFragment.newInstance());
        adapter.addFragment(LibraryFragment.newInstance());
        adapter.addFragment(AccountFragment.newInstance());
        viewPagerMain.setAbleSwipe(false);
        viewPagerMain.setAdapter(adapter);
        viewPagerMain.setOffscreenPageLimit(OFF_PAGE_LIMIT);
        handleViewModelBase(this, mainViewModel);

        if (!NetworkConnectReceiver.isConnected()) {
            TextView tvIndicatorNetwork = findViewById(R.id.tv_indicator_network);
            tvIndicatorNetwork.setBackgroundResource(android.R.color.holo_red_dark);
            tvIndicatorNetwork.setText(R.string.disconnected);
            tvIndicatorNetwork.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                viewPagerMain.setCurrentItem(PAGE_HOME);
                break;
            case R.id.navigation_lib_book:
                viewPagerMain.setCurrentItem(PAGE_LIBRARY);
                break;
            case R.id.navigation_account:
                viewPagerMain.setCurrentItem(PAGE_ACCOUNT);
                break;
        }

        return true;
    }

    @Override
    protected NetworkConnectReceiver.NetworkConnectionListener getNetworkListener() {
        return isConnected -> {
            @Nullable
            TextView tvIndicatorNetwork = findViewById(R.id.tv_indicator_network);

            if (!isConnected && tvIndicatorNetwork != null) {
                tvIndicatorNetwork.setBackgroundResource(android.R.color.holo_red_dark);
                tvIndicatorNetwork.setText(R.string.disconnected);
                tvIndicatorNetwork.setVisibility(View.VISIBLE);
            }

            if (isConnected && tvIndicatorNetwork != null && tvIndicatorNetwork.getVisibility() == View.VISIBLE) {
                tvIndicatorNetwork.setBackgroundResource(android.R.color.holo_green_dark);
                tvIndicatorNetwork.setText(R.string.connected);
                tvIndicatorNetwork.setVisibility(View.VISIBLE);

                new Handler().postDelayed(() -> {
                    tvIndicatorNetwork.animate()
                            .translationY(tvIndicatorNetwork.getHeight())
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tvIndicatorNetwork.setVisibility(View.GONE);
                                }
                            });
                    }, 1000);
            }
        };
    }

}