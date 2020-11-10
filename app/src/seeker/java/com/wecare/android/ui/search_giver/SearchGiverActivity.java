package com.wecare.android.ui.search_giver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.databinding.ActivitySearchGiverBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseTabsViewPagerAdapter;
import com.wecare.android.ui.search_giver.blocked.BlockedGiverFragment;
import com.wecare.android.ui.search_giver.favourite.FavouriteGiverFragment;
import com.wecare.android.ui.search_giver.search.SearchGiverFragment;
import com.wecare.android.ui.search_giver.suggested.SuggestedGiverFragment;
import com.wecare.android.utils.AppConstants;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

public class SearchGiverActivity extends BaseActivity<ActivitySearchGiverBinding, SearchGiverViewModel> implements SearchGiverNavigator, HasSupportFragmentInjector {

    public static final String TAG = SearchGiverActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    BaseTabsViewPagerAdapter viewPagerAdapter;

    /**/
    private TabLayout mTabLayout;
    /**/
    private ViewPager mViewPager;

    /**/
    SearchGiverViewModel viewModel;
    ActivitySearchGiverBinding binding;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchGiverActivity.class);
    }

    @Override
    public SearchGiverViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SearchGiverViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_giver;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUp() {

        mTabLayout = binding.searchTabLayout;
        mViewPager = binding.searchViewPager;
        if (getIntent().getExtras() != null) {
            addToolbar(R.id.toolbar, getString(R.string.search_results), true);
            viewPagerAdapter.addFrag(SuggestedGiverFragment.newInstance(getIntent().getExtras()), getString(R.string.suggested));
            viewPagerAdapter.addFrag(FavouriteGiverFragment.newInstance(getIntent().getExtras()), getString(R.string.favourite));
            viewPagerAdapter.addFrag(SearchGiverFragment.newInstance(getIntent().getExtras()), getString(R.string.general_search));
        } else {
            addToolbar(R.id.toolbar, getString(R.string.caregiver), true);
            viewPagerAdapter.addFrag(FavouriteGiverFragment.newInstance(null), getString(R.string.favourite));
            viewPagerAdapter.addFrag(BlockedGiverFragment.newInstance(null), getString(R.string.blocked));
            viewPagerAdapter.addFrag(SearchGiverFragment.newInstance(null), getString(R.string.general_search));
        }

        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
        viewPagerAdapter.setupAdapter(mViewPager, mTabLayout, 0);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onGiverPickedWithGiverSelection(SearchGiverResponse searchGiverResponse, FilterObject filterObject) {
        if (searchGiverResponse != null) {
//            searchGiverResponse.getCaregiver().setFilterAge(filterObject.getCaregiver_age());
//            searchGiverResponse.getCaregiver().setFilterLanguageID("1");//filterObject.getCaregiver_language_id()); //todo remove hard codded maybe use filter object.
            Intent data = new Intent();
            data.putExtra(AppConstants.ARGS_SELECTED_GIVER_PROFILE, searchGiverResponse);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void onGiverPicked() {
        //not used
    }

    @Override
    public void toggleSwipeToRefresh(boolean b) {
        //not used
    }
}
