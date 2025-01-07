package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.nextone.common.YardConfig;
import com.nextone.datandroid.R;
import com.nextone.model.yardConfigMode.YardConfigModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YardSettingFragment} factory method to
 * create an instance of this fragment.
 */
public class YardSettingFragment extends AbsTabFragment {



    private final YardConfigModel yardConfigModel;

    public YardSettingFragment() {
        // Required empty public constructor
        super(R.layout.fragment_recycler_view);
        this.yardConfigModel = YardConfig.getInstance().getYardConfigModel();
    }


    @Override
    public void saveData() {
        Log.i("YardSettingFragment", "saveData");
    }

    @Override
    public void updateData() {
        Log.i("YardSettingFragment", "updateData");
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {

    }
}