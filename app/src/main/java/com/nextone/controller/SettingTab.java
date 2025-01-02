package com.nextone.controller;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nextone.datandroid.customLayout.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.customLayout.tabLayout.TabLayoutCustomView;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.SensorSettingFragment;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.YardSettingFragment;

public class SettingTab {
    private final TabLayoutCustomView tabLayoutCustomView;

    public SettingTab(AppCompatActivity activity) {
        this.tabLayoutCustomView = new TabLayoutCustomView(activity);
        CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(activity);
        settingPagerAdapter.addFragment(new SensorSettingFragment(), "Cảm biến");
        settingPagerAdapter.addFragment(new YardSettingFragment(), "Thông tin sân");
        this.tabLayoutCustomView.setAdapter(settingPagerAdapter);
    }

    public void saveData() {
        tabLayoutCustomView.saveData();
    }

    public View getView() {
        return tabLayoutCustomView;
    }
}
