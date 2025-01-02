package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public abstract class AbsTabFragment extends Fragment {


    protected final int resource;
    public AbsTabFragment(int resource) {
        this.resource = resource;
    }

    public abstract void saveData();

    public abstract void updateData();

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(resource, container, false);
    }
}