package com.wecare.android.ui.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityTemplateBinding;
import com.wecare.android.ui.base.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class TemplateActivity extends BaseActivity<ActivityTemplateBinding, TemplateViewModel> implements TemplateNavigator {
//    ,HasSupportFragmentInjector

    public static final String TAG = TemplateActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
//    @Inject
//    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
//    @Inject
//    TemplateAdapter mTemplateAdapter;
//    @Inject
//    LinearLayoutManager mLayoutManager;

    TemplateViewModel viewModel;

    ActivityTemplateBinding binding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TemplateActivity.class);
    }

    @Override
    public TemplateViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TemplateViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_template;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentDispatchingAndroidInjector;
//    }

    @Override
    public void goBack() {

    }
}
