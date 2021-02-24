package com.fashion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fashion.core.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;
    public PagerAdapter(BaseActivity activity, List<Fragment> fragments) {
        super(activity.getSupportFragmentManager() , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }
    public PagerAdapter(BaseActivity activity) {
        super(activity.getSupportFragmentManager() , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
