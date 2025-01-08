package com.nextone.datandroid.customLayout.tabLayout;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class CustomPagerAdapter extends FragmentStateAdapter {

    @Getter
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public CustomPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        notifyDataSetChanged();
    }

    public void removeFragment(int position) {
        if (position < fragmentList.size()) {
            fragmentList.remove(position);
            fragmentTitleList.remove(position);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    public Fragment getFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public String getTitle(int position) {
        return fragmentTitleList.get(position);
    }
}

