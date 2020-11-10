package com.wecare.android.ui.create_order.location.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import br.com.ilhasoft.support.validation.Validator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.databinding.ActivityAddLocationBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.create_order.location.LocationNavigator;
import com.wecare.android.ui.create_order.location.LocationViewModel;
import com.wecare.android.ui.map.MapLocationPickerActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.WeCareUtils;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

import javax.inject.Inject;

public class AddEditLocationActivity extends BaseActivity<ActivityAddLocationBinding, LocationViewModel>
        implements LocationNavigator, OnMapReadyCallback {

    public static final String TAG = AddEditLocationActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    LocationViewModel viewModel;

    ActivityAddLocationBinding binding;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddEditLocationActivity.class);
        return intent;
    }

//    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
//        @Override
//        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
//            binding.selectedGenderTxt.setText(item.getTitle());
//            viewModel.getToBeFilledRelativeProfileResponse().setGender(item);
//            dialog.dismiss();
//        }
//    };

    SearchResultListener countriesResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.selectedCountryTxt.setText(item.getTitle());
            //request
            viewModel.getCities(item.getId() + "");
            viewModel.getToBeCreatedLocationResponse().setCountry(item);

            //reset city
            binding.cityTxt.setText("");
            viewModel.getToBeCreatedLocationResponse().setCity(null);

            dialog.dismiss();
        }
    };

    SearchResultListener citiesResultListener = new SearchResultListener<CityModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CityModel item, int position) {
            binding.cityTxt.setText(item.getTitle());
            viewModel.getToBeCreatedLocationResponse().setCity(item);
            dialog.dismiss();
        }
    };

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
        return R.layout.activity_add_location;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        //update toolbar title
        addToolbar(R.id.toolbar, getString(R.string.location), true);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(AppConstants.ARGS_KEY_LOCATION_OBJECT)) {
            getViewModel().isEditMode = true;
            getViewModel().setToBeCreatedLocationResponse(getIntent().getExtras().getParcelable(AppConstants.ARGS_KEY_LOCATION_OBJECT));

            fillUserInfoForEdit();
        }

        //request data
        viewModel.getActiveCountries();

        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        if (!getViewModel().isEditMode) {
            //open map if not edit mode
            openPlacesPicker();
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            validator.toValidate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQ_CODE_LOCATION_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    LocationModel locationModel = data.getExtras().getParcelable(AppConstants.ARGS_KEY_LOCATION_OBJECT);
                    viewModel.getToBeCreatedLocationResponse().setLongitude(String.valueOf(locationModel.getLongitude()));
                    viewModel.getToBeCreatedLocationResponse().setLatitude(String.valueOf(locationModel.getLatitude()));
                    if (locationModel.getName() != null) {
                        viewModel.getToBeCreatedLocationResponse().setName(locationModel.getName());
                        binding.locationNameEdt.setText(locationModel.getName());
                    }
                    setMarkerOnMap();
                }
            }
        }
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();

        if (isDropDownsValid()) {
            viewModel.getToBeCreatedLocationResponse().setName(WeCareUtils.getEditTextString(binding.locationNameEdt));
            viewModel.getToBeCreatedLocationResponse().setArea(WeCareUtils.getEditTextString(binding.areaEdt));
            viewModel.getToBeCreatedLocationResponse().setStreet_name(WeCareUtils.getEditTextString(binding.streetNameEdt));
            viewModel.getToBeCreatedLocationResponse().setBuilding_number(WeCareUtils.getEditTextString(binding.buildingNumberEdt));
            viewModel.getToBeCreatedLocationResponse().setFloor_number(WeCareUtils.getEditTextString(binding.floorNumberEdt));

            if (getViewModel().isEditMode) {
                //update
                viewModel.updateLocationData();
            } else {
                //add
                viewModel.createLocationRequest();
            }
        }
    }

    @Override
    public void onValidationError() {
        super.onValidationError();
    }

    private void openPlacesPicker() {
        Intent intent = new Intent(mContext, MapLocationPickerActivity.class);
        if (viewModel.getToBeCreatedLocationResponse().getLongitude() != null && viewModel.getToBeCreatedLocationResponse().getLatitude() != null) {
            LocationModel locationModel = new LocationModel();
            locationModel.setLatitude(Double.valueOf(viewModel.getToBeCreatedLocationResponse().getLatitude()));
            locationModel.setLongitude(Double.valueOf(viewModel.getToBeCreatedLocationResponse().getLongitude()));
            intent.putExtra(AppConstants.ARGS_KEY_LOCATION_OBJECT, locationModel);
        }
        startActivityForResult(intent, AppConstants.REQ_CODE_LOCATION_PLACE_PICKER);
    }

    private boolean isDropDownsValid() {

        if (viewModel.getToBeCreatedLocationResponse().getCountry() == null) {
            binding.countryLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.countryLayout.setError(null);


        if (viewModel.getToBeCreatedLocationResponse().getCity() == null) {
            binding.cityLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.cityLayout.setError(null);

        return true;
    }

    void enableDisableThisFields(int isVisible) {
//        validator.disableValidation(binding.iNameEdt);
//        validator.disableValidation(binding.iEdt);
//        binding.irentLinear.setVisibility(isVisible);
    }

    public void fillUserInfoForEdit() {
        if (getViewModel().getToBeCreatedLocationResponse() == null)
            return;

        //fill data to be edit.
        binding.locationNameEdt.setText(getViewModel().getToBeCreatedLocationResponse().getName());
        binding.areaEdt.setText(getViewModel().getToBeCreatedLocationResponse().getArea());
        binding.streetNameEdt.setText(getViewModel().getToBeCreatedLocationResponse().getStreet_name());
        binding.buildingNumberEdt.setText(getViewModel().getToBeCreatedLocationResponse().getBuilding_number());
        binding.floorNumberEdt.setText(getViewModel().getToBeCreatedLocationResponse().getFloor_number());
        binding.selectedCountryTxt.setText(getViewModel().getToBeCreatedLocationResponse().getCountry().getNationality());
        binding.cityTxt.setText(getViewModel().getToBeCreatedLocationResponse().getCity().getTitle());
    }

    private void showCountryOfService() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(mContext,
                getString(R.string.country),
                getString(R.string.general_search),
                null, getViewModel().getCountries(), countriesResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showCities() {
        if (getViewModel().getCities() == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(mContext,
                getString(R.string.city),
                getString(R.string.general_search),
                null, getViewModel().getCities(), citiesResultListener);

        simpleSearchDialogCompat.show();
    }

    @Override
    public void goBack() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void addNewLocationClicked() {
        //not used
    }

    @Override
    public void onCountryClick() {
        if (viewModel.getCountries() != null)
            showCountryOfService();
        else
            viewModel.getActiveCountries();
    }

    @Override
    public void onCityClick() {
        showCities();
    }

    @Override
    public void onMapPickerClick() {
        openPlacesPicker();
    }

    @Override
    public void onDeletedSuccessfully(UserLocationResponse locationResponse) {
        //not used
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMarkerOnMap();
    }

    private void setMarkerOnMap() {
        if (viewModel.getToBeCreatedLocationResponse().getLongitude() != null && viewModel.getToBeCreatedLocationResponse().getLatitude() != null) {
            if (!viewModel.getToBeCreatedLocationResponse().getLongitude().equals("null") && !viewModel.getToBeCreatedLocationResponse().getLatitude().equals("null")) {
                double latitude = Double.parseDouble(viewModel.getToBeCreatedLocationResponse().getLatitude());
                double longitude = Double.parseDouble(viewModel.getToBeCreatedLocationResponse().getLongitude());
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(latitude, longitude);
                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));


                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        }
    }

}
