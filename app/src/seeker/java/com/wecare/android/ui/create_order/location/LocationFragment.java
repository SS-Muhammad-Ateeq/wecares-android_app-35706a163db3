package com.wecare.android.ui.create_order.location;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ilhasoft.support.validation.Validator;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.databinding.FragmentLocationBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.create_order.location.add.AddEditLocationActivity;
import com.wecare.android.ui.map.MapLocationPickerActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;
import java.util.List;

public class LocationFragment extends BaseFragment<FragmentLocationBinding, LocationViewModel> implements LocationNavigator, LocationAdapter.LocationAdapterListener {

    public static final String TAG = LocationFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    LocationAdapter locationAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private LocationViewModel viewModel;

    private FragmentLocationBinding binding;

    private int selectedPosition = -1;
    private SelectionTracker<Long> selectionTracker;

    /**/
    public static LocationFragment newInstance() {
        Bundle args = new Bundle();
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_location;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().fetchLocations();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        //validation
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        setUp(savedInstanceState);
        subscribeToLiveData();

    }

    @Override
    public void onValidationSuccess() {
//        if (isDropDownsValid()) {
//            viewModel.getDataManager()..setValue(WeCareUtils.getEditTextString(binding.edt));
        //your action here;
//        }
    }

    @Override
    public void onValidationError() {
        super.onValidationError();
    }


    @Override
    public LocationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel.class);
        return viewModel;
    }

    private void setUp(Bundle savedInstanceState) {
        locationAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.locationRecycler.setLayoutManager(mLayoutManager);
        binding.locationRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.locationRecycler.setAdapter(locationAdapter);

        selectionTracker = new SelectionTracker.Builder<>("mySelectionPosition",
                binding.locationRecycler,
                new LocationAdapter.KeyProvider(binding.locationRecycler.getAdapter()),
                new LocationAdapter.SelectorItemDetailsLookup(binding.locationRecycler),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build();
//                .withSelectionPredicate(new LocationAdapter.DetailsPredicate()).build();

        locationAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                if (selected) {
                    selectedPosition = ((Long) key).intValue();
                } else {
                    selectedPosition = -1;
                }
            }
        });

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

    private void subscribeToLiveData() {
        viewModel.getLocationListLiveData().observe(this, new Observer<List<UserLocationResponse>>() {
            @Override
            public void onChanged(@Nullable List<UserLocationResponse> profileResponseList) {
                viewModel.addLocationItemsToList(profileResponseList);
                setSelectedLocation(profileResponseList);
            }
        });
    }

    public void setSelectedLocation(List<UserLocationResponse> userLocationResponseList) {
        if (getActivity() != null && getActivity() instanceof CreateOrderActivity && ((CreateOrderActivity) getActivity()).isReOrderFlow) {
            if (((CreateOrderActivity) getActivity()).getReOrderModel().getLocation() != null) {
                String selectedLocation = ((CreateOrderActivity) getActivity()).getReOrderModel().getRef_location_id();
                for (int i = 0; i < userLocationResponseList.size(); i++) {
                    UserLocationResponse userLocationResponse = userLocationResponseList.get(i);
                    if (userLocationResponse.getId().equals(selectedLocation))
                        selectionTracker.select((long) i);
                }
            }
        }
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void addNewLocationClicked() {
        Intent intent = AddEditLocationActivity.getStartIntent(getBaseActivity());
        startActivity(intent);
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
        getViewModel().getLocationObservableArrayList().remove(locationResponse);
        locationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getBaseActivity().setTitle(getString(R.string.location));
    }

    @Override
    public void onItemClicked(UserLocationResponse userLocationResponseItem) {
        Intent intent = AddEditLocationActivity.getStartIntent(getBaseActivity());
        intent.putExtra(AppConstants.ARGS_KEY_LOCATION_OBJECT, userLocationResponseItem);
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(UserLocationResponse userLocationResponseItem) {
        DialogFactory.createReactDialog(getBaseActivity(), getString(R.string.delete),
                getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), null,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getViewModel().deleteLocationData(userLocationResponseItem);
                    }
                }, null);
    }

    @Override
    public void onEditClicked(UserLocationResponse userLocationResponseItem) {

    }

    public UserLocationResponse getSelectedLocation() {
        UserLocationResponse locationResponse = null;
        if (selectionTracker != null && selectedPosition != -1) {
            locationResponse = locationAdapter.getItemAtPosition(selectedPosition);
        } else {
            if (getActivity() != null && ((CreateOrderActivity) getActivity()).isReOrderFlow) {
                locationResponse = ((CreateOrderActivity) getActivity()).getReOrderModel().getLocation();
            }
        }
        return locationResponse;
    }

}
