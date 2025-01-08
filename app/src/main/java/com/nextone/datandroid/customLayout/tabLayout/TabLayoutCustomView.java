package com.nextone.datandroid.customLayout.tabLayout;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.AbsTabFragment;

import lombok.Setter;

public class TabLayoutCustomView extends AbsTabFragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Setter
    private OnAdapterCallback callback;

    private TabLayoutMediator tabLayoutMediator;
    private CustomPagerAdapter adapter;


    public TabLayoutCustomView() {
        super(R.layout.tab_layout_custom);
    }

    public static interface OnAdapterCallback{
        CustomPagerAdapter callback(ViewPager2 pager);
    }


    private void setAdapter() {
        if (this.callback == null) {
            return;
        }
        this.adapter = this.callback.callback(this.viewPager);
        if (this.tabLayoutMediator != null) {
            this.tabLayoutMediator.detach();
            this.viewPager.setAdapter(this.adapter);
            this.tabLayoutMediator.attach();
        }
    }

    public void saveData() {
        if (adapter != null) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                Fragment fragment = adapter.getFragment(i);
                if (fragment instanceof AbsTabFragment tabFragment) {
                    tabFragment.saveData();
                }
            }
        }
    }

    public int getCurrentItemIndex(){
        if(viewPager == null){
            return -1;
        }
        return this.viewPager.getCurrentItem();
    }

    @Override
    public void updateData() {

    }

    public void removeCurrentItem(){
        removeItem(getCurrentItemIndex());
    }


    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int positionToRemove) {
        if (positionToRemove < 0) {
            return;
        }
        CustomPagerAdapter adapter = (CustomPagerAdapter) this.viewPager.getAdapter();
        if (adapter != null) {
            adapter.removeFragment(positionToRemove);
            adapter.notifyDataSetChanged();
        }
        if (tabLayout.getTabCount() > positionToRemove) {
            tabLayout.removeTabAt(positionToRemove);
        }
        tabLayoutMediator.detach();
        tabLayoutMediator.attach();
    }

    @Override
    protected void onInitViewCreated(View view) {
        setAdapter();
    }

    @Override
    protected void onInitCreateView(View view) {
        this.tabLayout = view.findViewById(R.id.tabLayoutName);
        this.viewPager = view.findViewById(R.id.viewPager);
        this.tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getTitle(position));
        });
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
