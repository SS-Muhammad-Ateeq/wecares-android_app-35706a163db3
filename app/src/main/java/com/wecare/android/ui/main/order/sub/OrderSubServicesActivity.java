package com.wecare.android.ui.main.order.sub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ActivityOrderSubServicesBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.home.sub.SubServicesNavigator;
import com.wecare.android.ui.main.home.sub.SubServicesViewModel;
import com.wecare.android.utils.AppConstants;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class OrderSubServicesActivity extends BaseActivity<ActivityOrderSubServicesBinding, SubServicesViewModel> implements
        SubServicesNavigator, OrderSubServicesAdapter.SubServiceAdapterListener, HasSupportFragmentInjector {

    public static final String TAG = OrderSubServicesActivity.class.getSimpleName();
    String pickingType;

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    OrderSubServicesAdapter orderSubServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private SelectionTracker<Long> selectionTracker;

    SubServicesViewModel viewModel;

    ActivityOrderSubServicesBinding binding;

    MainServiceModel selectedServiceResponse;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, OrderSubServicesActivity.class);
    }

    @Override
    public SubServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SubServicesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_sub_services;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();

        addToolbar(R.id.toolbar, getString(R.string.wecare), true);

        viewModel.setNavigator(this);

        setUp(savedInstanceState);
        pickingType = getIntent().getStringExtra(AppConstants.ARGS_KEY_SUB_SERVICES_PICKING_TYPE);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(AppConstants.ARGS_KEY_SERVICES)) {
            selectedServiceResponse = getIntent().getExtras().getParcelable(AppConstants.ARGS_KEY_SERVICES);

            if (pickingType.equals(AppConstants.ARGS_KEY_SUB_SERVICES_EDIT))
                setItemsSelection();

            if (selectedServiceResponse != null) {
                //background rounded color programmatically
                binding.subServicesTitleTxt.setBackgroundResource(R.drawable.rounded_top_corners);
                GradientDrawable drawable = (GradientDrawable) binding.subServicesTitleTxt.getBackground();
                drawable.setColor(Color.parseColor(selectedServiceResponse.getColor()));
                //set title
                binding.subServicesTitleTxt.setText(selectedServiceResponse.getServiceName());
                //update recycler list.
//                orderSubServicesAdapter.addItems(selectedServiceResponse.getSubServiceResponseList());
                subscribeToLiveData();
                getViewModel().setSubServiceListLiveDataValue(selectedServiceResponse.getSubServiceResponseList());
            }
        }
    }

    private void setItemsSelection() {
        if (selectedServiceResponse.getSubServiceResponseList() == null)
            return;

        for (int i = 0; i < selectedServiceResponse.getSubServiceResponseList().size(); i++) {
            SubServiceResponse subServiceResponse = selectedServiceResponse.getSubServiceResponseList().get(i);
            if (subServiceResponse.getOrderSelected() == AppConstants.PHP_TRUE_RAW)
                selectionTracker.select((long) i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
//                validator.toValidate();
                returnSelectedItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUp(Bundle savedInstanceState) {
        orderSubServicesAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.subServiceRecycler.setLayoutManager(mLayoutManager);
        binding.subServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.subServiceRecycler.setAdapter(orderSubServicesAdapter);

        selectionTracker = new SelectionTracker.Builder<>("mSelectionPosition",
                binding.subServiceRecycler,
                new OrderSubServicesAdapter.KeyProvider(binding.subServiceRecycler.getAdapter()),
                new OrderSubServicesAdapter.SelectorItemDetailsLookup(binding.subServiceRecycler),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectAnything()).build();
//                .withSelectionPredicate(new RelativeProfileAdapter.DetailsPredicate()).build();

        orderSubServicesAdapter.setSelectionTracker(selectionTracker);


        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
//                if (selected) {
//                    selectedPosition = ((Long) key).intValue();
//                } else {
//                    selectedPosition = -1;
//                }
            }
        });

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void returnSelectedItem() {
        ArrayList<SubServiceResponse> subServiceResponseList = new ArrayList<>();

        for (Long aLong : selectionTracker.getSelection()) {
            int selectedPosition = (aLong).intValue();
            SubServiceResponse subServiceResponse = orderSubServicesAdapter.getItemAtPosition(selectedPosition);
            subServiceResponseList.add(subServiceResponse);
        }

        if (subServiceResponseList.size() != 0) {
            Intent data = new Intent();
            data.putParcelableArrayListExtra(AppConstants.ARGS_SELECTED_ORDER_SUB_SERVICE_LIST, subServiceResponseList);
            setResult(RESULT_OK, data);
            finish();
        } else {
            showToast(getString(R.string.please_select_one_sub_service_at));
        }

    }

    private void subscribeToLiveData() {
        viewModel.getSubServiceListLiveData().observe(this, new Observer<List<SubServiceResponse>>() {
            @Override
            public void onChanged(@Nullable List<SubServiceResponse> subServiceResponseList) {
                viewModel.addSubServicesItemsToList(subServiceResponseList);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onOrderNowClick() {
        //not used here.
    }

    @Override
    public void onItemClicked(SubServiceResponse subServiceResponse) {
//        subServiceResponse.setColor(selectedServiceResponse.getColor());
//        replaceFragment(SubDetailsFragment.newInstance(subServiceResponse), true, true,true, R.id.content);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
