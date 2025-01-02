package com.nextone.datandroid.customLayout.tabLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.AbsTabFragment;

public class TabLayoutCustomView extends AbsCustomConstraintLayout {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private TabLayoutMediator tabLayoutMediator;
    private CustomPagerAdapter adapter;


    public TabLayoutCustomView(Context context) {
        super(context);
        init(R.layout.setting_base, true);
    }

    public TabLayoutCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.setting_base, true);
    }

    public TabLayoutCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.setting_base, true);
    }

    @Override
    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        this.tabLayout = findViewById(R.id.tabSettingElem);
        this.viewPager = findViewById(R.id.viewPager);
        this.tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        });
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = adapter.getFragment(position);
                if (fragment instanceof AbsTabFragment tabFragment) {
                    tabFragment.saveData();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setAdapter(CustomPagerAdapter adapter) {
        if (adapter == null) return;
        this.adapter = adapter;
        this.tabLayoutMediator.detach();
        this.viewPager.setAdapter(adapter);
        this.tabLayoutMediator.attach();
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

    @SuppressLint("NotifyDataSetChanged")
    public void removeFragment(int positionToRemove) {
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

}
