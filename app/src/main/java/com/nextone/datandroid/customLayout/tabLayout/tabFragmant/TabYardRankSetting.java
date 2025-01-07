package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.util.Log;
import android.view.View;

import com.nextone.common.YardConfig;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.customLayout.tabLayout.TabLayoutCustomView;
import com.nextone.model.yardConfigMode.YardConfigModel;

public class TabYardRankSetting extends AbsTabFragment {

    private final YardConfigModel yardConfigModel;

    private final TabLayoutCustomView tabLayoutCustomView;

    public TabYardRankSetting() {
        super(R.layout.setting_yard);
        this.yardConfigModel = YardConfig.getInstance().getYardConfigModel();
        this.tabLayoutCustomView = new TabLayoutCustomView();
        this.tabLayoutCustomView.setCallback(pager -> {
            CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(requireActivity());
            settingPagerAdapter.addFragment(new YardSettingFragment(), "Hạng B");
            settingPagerAdapter.addFragment(new YardSettingFragment(), "Hạng C");
            return settingPagerAdapter;
        });
    }

    @Override
    public void saveData() {
        Log.i("TabYardRankSetting", "saveData");
    }

    @Override
    public void updateData() {
        Log.i("TabYardRankSetting", "update");
    }

    @Override
    protected void onInitViewCreated(View view) {
        addChildFragment(R.id.settingYardContainer, tabLayoutCustomView, "YardSetting", true);
    }

    @Override
    protected void onInitCreateView(View view) {
        Log.i(getClass().getSimpleName(), "init");
    }
}
