package com.wecare.android.ui.main.order;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is generic adapter used in ViewPager to handle its list of fragment.
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;
    private List<Fragment> fragments = new ArrayList<>();


    public OrderPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override

    public Fragment getItem(int position) {

        return this.fragments.get(position);

    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);

    }

    @Override

    public int getCount() {

        return this.fragments.size();

    }
}