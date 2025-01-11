package com.nextone.datandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class AbsFragment extends Fragment {
    protected final int resource;

    protected View view;

    public AbsFragment(int resource) {
        this.resource = resource;
    }

    protected abstract void onInitViewCreated(View view);

    protected abstract void onInitCreateView(View view);


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.onInitViewCreated(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(resource, container, false);
        onInitCreateView(view);
        return view;
    }

    public void setChildFragment(int resource, Fragment fragment, String tab, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(resource, fragment, tab);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        Log.i(getClass().getSimpleName(), "fragment toBackStack:" +
                getChildFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.view = null;
    }

    public void removeChildFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }
}
