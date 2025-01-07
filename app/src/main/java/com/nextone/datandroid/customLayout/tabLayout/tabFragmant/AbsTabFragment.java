package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.util.Log;

import com.nextone.datandroid.AbsFragment;

public abstract class AbsTabFragment extends AbsFragment {

    public AbsTabFragment(int resource) {
        super(resource);
    }

    public abstract void saveData();

    public abstract void updateData();


    @Override
    public void onResume() {
        super.onResume();
        try {
            updateData();
        } catch (Exception e) {
            Log.e("AbsTabFragment", e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            saveData();
        } catch (Exception e) {
            Log.e("AbsTabFragment", e.getMessage());
        }
    }


}