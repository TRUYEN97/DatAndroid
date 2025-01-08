package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.view.View;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.customLayout.tabLayout.TabLayoutCustomView;

public class TabYardRankSetting extends AbsTabFragment {

    private final TabLayoutCustomView tabLayoutCustomView;

    public TabYardRankSetting() {
        super(R.layout.setting_yard);
        this.tabLayoutCustomView = new TabLayoutCustomView();
        this.tabLayoutCustomView.setCallback(pager -> {
            CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(requireActivity());
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
        addChildFragment(R.id.settingYardContainer, tabLayoutCustomView, "YardSetting", true);
    }

    @Override
    protected void onInitCreateView(View view) {
    }
}
