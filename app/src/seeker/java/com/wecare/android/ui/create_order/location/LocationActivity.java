package com.wecare.android.ui.create_order.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.databinding.ActivityLocationBinding;
import com.wecare.android.ui.base.BaseActivity;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

public class LocationActivity extends BaseActivity<ActivityLocationBinding, LocationViewModel>
        implements LocationNavigator, HasSupportFragmentInjector {

    public static final String TAG = LocationActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    LocationViewModel viewModel;
    ActivityLocationBinding binding;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @Override
    public LocationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        addToolbar(R.id.toolbar, getString(R.string.location), true);

        replaceFragment(LocationFragment.newInstance(), true, false, true, R.id.container);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void addNewLocationClicked() {
        //not used here
    }

    @Override
    public void onCountryClick() {

    }

    @Override
    public void onCityClick() {

    }

    @Override
    public void onMapPickerClick() {

    }

    @Override
    public void onDeletedSuccessfully(UserLocationResponse locationResponse) {
        //not used
    }

}
