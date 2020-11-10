package com.wecare.android.ui.template;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityTemplateBinding;
import com.wecare.android.ui.base.BaseFragment;

import javax.inject.Inject;

public class TemplateFragment extends BaseFragment<ActivityTemplateBinding, TemplateViewModel> implements TemplateNavigator {

    public static final String TAG = TemplateFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    //    @Inject
//    TemplateAdapter mTemplateAdapter;
//    @Inject
//    LinearLayoutManager mLayoutManager;

    TemplateViewModel viewModel;

    ActivityTemplateBinding binding;


    public static TemplateFragment newInstance() {
        Bundle args = new Bundle();
        TemplateFragment fragment = new TemplateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
//        mTemplateAdapter.setListener(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
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
    public void goBack() {

    }
}
