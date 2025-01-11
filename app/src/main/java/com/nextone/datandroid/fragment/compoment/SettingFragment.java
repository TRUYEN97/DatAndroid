package com.nextone.datandroid.fragment.compoment;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.fragment.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.fragment.tabLayout.TabLayoutCustomFragment;
import com.nextone.datandroid.fragment.tabLayout.tabFragmant.SensorSettingFragment;
import com.nextone.datandroid.fragment.tabLayout.tabFragmant.TabYardRankSetting;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends AbsFragment {

    public SettingFragment() {
        super(R.layout.fragment_setting);
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        TabLayoutCustomFragment tabLayoutCustomView = (TabLayoutCustomFragment) getChildFragmentManager().findFragmentById(R.id.fragmentSetting);
        assert tabLayoutCustomView != null;
        CustomPagerAdapter settingPagerAdapter= new CustomPagerAdapter(requireActivity());
        settingPagerAdapter.addFragment(new SensorSettingFragment(), "Cảm biến");
        settingPagerAdapter.addFragment(new TabYardRankSetting(), "Thông tin sân");
        tabLayoutCustomView.setCallback(pager -> settingPagerAdapter);
    }
}