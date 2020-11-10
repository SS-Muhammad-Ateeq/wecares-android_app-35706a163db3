package com.wecare.android.ui.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mihyar on 8/14/2015.
 */
public class BaseTabsViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Drawable> mFragmentIconList = new ArrayList<>();
    private final List<View> mFragmentCustomViewList = new ArrayList<>();
    private Context mContext;
    private int currentFragPosition = 0;
    private FragmentManager oFragmentManager;
//    private int previousFragPosition = -1;

    private ViewPager viewPager;
    private TabLayout tabLayout;


    //View pager scroll listener variables
    private int mPreviousScrollState;
    private int mScrollState;

    public BaseTabsViewPagerAdapter(Context context, FragmentManager manager) {
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

    public void addFrag(Fragment fragment, Drawable titleImage) {
        addFrag(fragment, "", titleImage, null);
    }

    public void addFrag(Fragment fragment, Integer titleImage) {
        Drawable drawable = mContext.getResources().getDrawable(titleImage);
        addFrag(fragment, "", drawable, null);
    }

    public void addFrag(Fragment fragment, String title, Drawable titleImage) {
        addFrag(fragment, title, titleImage, null);
    }

    public void addFrag(Fragment fragment, String title, Integer titleImage) {
        Drawable drawable = mContext.getResources().getDrawable(titleImage);
        addFrag(fragment, title, drawable, null);
    }

    public void addFrag(Fragment fragment, View customView) {
        addFrag(fragment, "", null, customView);
    }

    private void addFrag(Fragment fragment, String title, Drawable titleImage, View CustomView) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(titleImage);
        mFragmentCustomViewList.add(CustomView);
    }

    public void setupAdapter(ViewPager viewPager, TabLayout tabLayout) {
        setupAdapter(viewPager, tabLayout, 0);
    }

    /**
     * @param viewPager   the viewpager you want to set this adapter to
     * @param tab_layout  each fragment in the adapter will be connected to a tab in this TabLayout
     * @param currentPage the initial tab(fragment) to display
     */
    public void setupAdapter(ViewPager viewPager, TabLayout tab_layout, int currentPage) {
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.viewPager.setCurrentItem(currentPage);
        this.viewPager.addOnPageChangeListener(this);


        this.tabLayout = tab_layout;
        setTabsFromPagerAdapter(this, currentPage);
        this.tabLayout.addOnTabSelectedListener(this);
        addTablayout(this.tabLayout);
        currentFragPosition = currentPage;


    }

    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getFragmentForPosition(currentFragPosition);
//        return (BaseFragment) mFragmentList.get(currentFragPosition);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(mFragmentTitleList.get(position));
    }

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
        if (this.tabLayout != null) {
            boolean updateText = this.mScrollState == 1 || this.mScrollState == 2 && this.mPreviousScrollState == 1;
            tabLayout.setScrollPosition(position, positionOffset, updateText);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (this.tabLayout != null) {
            if (tabLayout.getSelectedTabPosition() != position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
            }
        }
        currentFragPosition = position;
        UpdateFragment(position);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        BaseFragment frag = (BaseFragment) getFragmentForPosition(tab.getPosition());
//        if (frag != null){
        frag.OnViewRemoved();
//        }

//        ((BaseFragment) mFragmentList.get(tab.getPosition())).OnViewRemoved();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        BaseFragment frag = (BaseFragment) getFragmentForPosition(tab.getPosition());
//        if (frag != null){
        frag.OnTabReselected();
//        }
//        ((BaseFragment) mFragmentList.get(tab.getPosition())).OnTabReselected();
    }

    /**
     * Populate our tab content from the given {@link PagerAdapter}.
     * <p>
     * Any existing tabs will be removed first. Each tab will have it's text set to the value
     * returned from {@link PagerAdapter#getPageTitle(int)}
     * </p>
     *
     * @param adapter the adapter to populate from
     */
    private void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter, int selectedTab) {
        this.tabLayout.removeAllTabs();
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            this.tabLayout.addTab(this.tabLayout.newTab().setText(adapter.getPageTitle(i)), i == selectedTab ? true : false);
        }
    }

    /**
     * Use this Method
     *
     * @param tabLayout
     */
    private void addTablayout(TabLayout tabLayout) {
//        for (int i= 0; i < tabLayout.getTabCount(); i++){
//            View view = LayoutInflater.from(mContext).inflate(R.layout.tabitem_customview, null, false);
//            ImageView iconView = (ImageView) view.findViewById(R.id.iv_tabItem_Icon);
//            if (mFragmentIconList.get(i) != null){
//                iconView.setImageDrawable(mFragmentIconList.get(i));
//                if (i == tabLayout.getSelectedTabPosition()){
//                    iconView.setSelected(true);
//                } else {
//                    iconView.setSelected(false);
//                }
//                iconView.setVisibility(View.VISIBLE);
//            } else{
//                iconView.setVisibility(View.GONE);
//            }
//
//            TextView textView = (TextView) view.findViewById(R.id.tv_tabItem_lable);
//            if (!SampleUtil.isNullOrEmpty(mFragmentTitleList.get(i))){
//                textView.setText(mFragmentTitleList.get(i));
//                textView.setVisibility(View.VISIBLE);
//            } else {
//                textView.setVisibility(View.GONE);
//            }
//
//            tabLayout.getTabAt(i).setCustomView(view);
//        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            if (mFragmentCustomViewList.get(i) != null) {
                tabLayout.getTabAt(i).setCustomView(mFragmentCustomViewList.get(i));
                if (i == tabLayout.getSelectedTabPosition()) {
                    ((TabLayout.Tab) tabLayout.getTabAt(i)).getCustomView().setSelected(true);
                }
            } else {
                if (!WeCareUtils.isNullOrEmpty(mFragmentTitleList.get(i))) {
                    tabLayout.getTabAt(i).setText(mFragmentTitleList.get(i));
                }
                if (mFragmentIconList.get(i) != null) {
                    tabLayout.getTabAt(i).setIcon(mFragmentIconList.get(i));
                }
            }
        }

//        for (int i= 0; i < mFragmentIconList.size(); i++){
//            tabLayout.getTabAt(i).setIcon(mFragmentIconList.get(i));
//        }
//
//        for (int i= 0; i < mFragmentTitleList.size(); i++){
//            if (!SampleUtil.isNullOrEmpty(mFragmentTitleList.get(i))){
//                tabLayout.getTabAt(i).setText(mFragmentTitleList.get(i));
//            }
//        }

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