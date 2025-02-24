package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.view.View;

import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.fragment.tabLayout.TabLayoutCustomFragment;

public class TabYardRankSetting extends AbsTabFragment {

    private final TabLayoutCustomFragment tabLayoutCustomView;

    public TabYardRankSetting() {
        super(R.layout.setting_yard);
        this.tabLayoutCustomView = new TabLayoutCustomFragment();
        this.tabLayoutCustomView.setCallback(pager -> {
            CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(requireActivity());
            settingPagerAdapter.addFragment(new YardServerSettingFragment(), "Server");
            settingPagerAdapter.addFragment(new YardSettingFragment(1), "Hạng B");
            settingPagerAdapter.addFragment(new YardSettingFragment(2), "Hạng C");
            settingPagerAdapter.addFragment(new YardSettingFragment(3), "Hạng D");
            settingPagerAdapter.addFragment(new YardSettingFragment(4), "Hạng E");
            return settingPagerAdapter;
        });
    }

    @Override
    public void saveData() {
    }

    @Override
    public void updateData() {
    }

    @Override
    protected void onInitViewCreated(View view) {
        setChildFragment(R.id.settingYardContainer, tabLayoutCustomView, "YardSetting", true);
    }

    @Override
    protected void onInitCreateView(View view) {
    }
}
