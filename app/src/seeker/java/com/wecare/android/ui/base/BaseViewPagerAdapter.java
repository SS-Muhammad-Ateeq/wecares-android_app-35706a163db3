package com.wecare.android.ui.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mihyar on 02/12/2017.
 */
public class BaseViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Drawable> mFragmentIconList = new ArrayList<>();
    private final List<View> mFragmentCustomViewList = new ArrayList<>();

    private Context mContext;
    private int currentFragPosition = 0;
    private FragmentManager oFragmentManager;

    private ViewPager viewPager;

    //View pager scroll listener variables
    private int mPreviousScrollState;
    private int mScrollState;

    public BaseViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.oFragmentManager = manager;
        this.mContext = context;
    }

    public static String makeFragmentName(int containerViewId, long id) {
        return "android:switcher:" + containerViewId + ":" + id;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        addFrag(fragment, title, null, null);
    }

    private void addFrag(Fragment fragment, String title, Drawable titleImage, View CustomView) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(titleImage);
        mFragmentCustomViewList.add(CustomView);
    }

    public void setupAdapter(ViewPager viewPager) {
        setupAdapter(viewPager, 0);
    }

    /**
     * @param viewPager   the viewpager you want to set this adapter to
     * @param currentPage the initial tab(fragment) to display
     */
    public void setupAdapter(ViewPager viewPager, int currentPage) {
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.viewPager.setCurrentItem(currentPage);
        this.viewPager.addOnPageChangeListener(this);

        currentFragPosition = currentPage;
    }

    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getFragmentForPosition(currentFragPosition);
//        return (BaseFragment) mFragmentList.get(currentFragPosition);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return String.valueOf(mFragmentTitleList.get(position));
//    }

    public int getCurrentFragmentIndex() {
        return currentFragPosition;
    }

    public void UpdateFragment(int position) {
        Log.d("app:UpdateFragment", String.valueOf(position));
        BaseFragment frag = (BaseFragment) getFragmentForPosition(position);
//        if (frag != null){
        frag.OnUpdateView();
//        }
//        ((BaseFragment) mFragmentList.get(position)).OnUpdateView();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.mPreviousScrollState = this.mScrollState;
        this.mScrollState = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentFragPosition = position;
        UpdateFragment(position);
    }

    public Fragment getFragmentForPosition(int position) {
        String tag = makeFragmentName(viewPager.getId(), getItemId(position));
        Fragment fragment = oFragmentManager.findFragmentByTag(tag);
        return fragment;
    }

    public void switchViewPagerPage(int position) {
        viewPager.setCurrentItem(position);
    }

}