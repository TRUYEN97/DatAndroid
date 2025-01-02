package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.nextone.datandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YardSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YardSettingFragment extends AbsTabFragment {

    public YardSettingFragment() {
        // Required empty public constructor
        super(R.layout.fragment_yard_setting);
    }

    @Override
    public void saveData() {
        Log.i("YardSettingFragment", "saveData");
    }

    @Override
    public void updateData() {
        Log.i("YardSettingFragment", "updateData");
    }
}