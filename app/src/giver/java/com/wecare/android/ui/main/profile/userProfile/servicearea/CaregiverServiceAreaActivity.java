package com.wecare.android.ui.main.profile.userProfile.servicearea;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.databinding.ActivityCaregiverServiceAreaBinding;
import com.wecare.android.interfaces.CustomMapLocationCallBack;
import com.wecare.android.interfaces.LocationCallBack;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.MainActivity;
import com.wecare.android.ui.map.MapLocationPickerActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

public class CaregiverServiceAreaActivity extends BaseActivity<ActivityCaregiverServiceAreaBinding, CaregiverServiceAreaViewModel> implements CaregiverServiceAreaNavigator, LocationListener, OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    ActivityCaregiverServiceAreaBinding binding;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationCallBack locationCallBack;


    boolean isCurrentLocation = true;

    int PLACE_PICKER_REQUEST = 700;



    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;


    LocationManager locationManager;
    public static final int LOCATION_REFRESH_TIME = 100;
    public static final int LOCATION_REFRESH_DISTANCE = 100;
    public static final int REQUEST_LOCATION_PERMESSION = 1000;

    @Inject
    ViewModelProviderFactory factory;
    private CaregiverServiceAreaViewModel viewModel;


    @Override
    public CaregiverServiceAreaViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CaregiverServiceAreaViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_caregiver_service_area;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.submitting_services_area), true);
        viewModel.setNavigator(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getString(R.string.error_gps_is_off));
            dialog.setCancelable(false);
            dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    init();
                }
            });
            dialog.show();
        } else {
            init();
        }

        //by default
        currentLocationClicked();

        viewModel.getServiceLocation();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void init() {
        getMyLocation(REQUEST_LOCATION_PERMESSION, new LocationCallBack() {
            @Override
            public void onLocationResponse(Location location) {
                if (location != null) {
                 viewModel.getLocationModel().setLatitude(location.getLatitude());
                 viewModel.getLocationModel().setLongitude(location.getLongitude());
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                        (ActivityCompat.checkSelfPermission(CaregiverServiceAreaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(CaregiverServiceAreaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                                      setMarkerOnMap();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItem:
                viewModel.updateUserServiceLocation();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CaregiverServiceAreaActivity.class);
        return intent;
    }

    @Override
    public void currentLocationClicked() {
        isCurrentLocation = true;
        viewModel.getLocationModel().setType(AppConstants.MY_LAST_LOCATION);
        setUpLocationButtonViews();
    }

    @Override
    public void customLocationClicked() {
        isCurrentLocation = false;
        viewModel.getLocationModel().setType(AppConstants.SPECIFIC_LOCATION_LOCATION);
        setUpLocationButtonViews();
    }

    @Override
    public void serviceLocationUpdatedSuccessfully() {
        DialogFactory.createFeedBackDialog(this, "", getString(R.string.saved_successfully), getString(R.string.ok), getResources().getDrawable(R.drawable.success_img), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                finish();
            }
        });
    }

    @Override
    public void serviceLocationFetchedSuccessfully(LocationModel locationModel) {
        if (locationModel.getType() == AppConstants.MY_LAST_LOCATION) {
            currentLocationClicked();
        } else {
            customLocationClicked();
        }
    }

    private void setUpLocationButtonViews() {
        binding.currentLocationCheckbox.setChecked(isCurrentLocation);
        binding.customLocationCheckbox.setChecked(!isCurrentLocation);
        if (isCurrentLocation) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                        LOCATION_REFRESH_DISTANCE, this);
                goToMyLocation();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMESSION);
            }
        } else {
            openPlacesPicker();
        }
    }


    private void openPlacesPicker() {
        Intent intent = new Intent(CaregiverServiceAreaActivity.this, MapLocationPickerActivity.class);

        if (viewModel.getLocationModel().getLongitude()!=null && viewModel.getLocationModel().getLatitude()!=null)
        intent.putExtra(AppConstants.ARGS_KEY_LOCATION_OBJECT,viewModel.getLocationModel());
        startActivityForResult(intent,PLACE_PICKER_REQUEST);
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        goToMyLocation();
        }
    }

    CustomMapLocationCallBack callBack = new CustomMapLocationCallBack() {
        @Override
        public void locationPicked(LocationModel locationModel) {
            locationModel.setType(AppConstants.SPECIFIC_LOCATION_LOCATION);
            viewModel.setLocationModel(locationModel);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
              viewModel.setLocationModel(data.getExtras().getParcelable(AppConstants.ARGS_KEY_LOCATION_OBJECT));
                setMarkerOnMap();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        mMap.getUiSettings().setAllGesturesEnabled(false);


        if (viewModel.getLocationModel().getLongitude() != null || viewModel.getLocationModel().getLatitude() != null) {
            setMarkerOnMap();
        }
    }

    public void goToMyLocation() {

      getMyLocation(AppConstants.PERMISSION_ACCESS_GO_TO_MY_LOCATION, new LocationCallBack() {
            @Override
            public void onLocationResponse(Location location) {
                if (location == null) {
                    showToast(getString(R.string.gps_access_denied));
                    return;
                } else {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                        viewModel.getLocationModel().setLongitude(longitude);
                        viewModel.getLocationModel().setLatitude(latitude);
                    setMarkerOnMap();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel.getLocationModel().getLongitude() != null || viewModel.getLocationModel().getLatitude() != null) {
            setMarkerOnMap();
        }    }

    private void setMarkerOnMap(){
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(viewModel.getLocationModel().getLatitude(),viewModel.getLocationModel().getLongitude());
        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Clears the previously touched position
        mMap.clear();

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(viewModel.getLocationModel().getLatitude(), viewModel.getLocationModel().getLongitude()), 14.0f));


        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    public void getMyLocation(int permissionActionId, LocationCallBack locationCallBack) {

        this.locationCallBack = locationCallBack;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {



            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, permissionActionId);
            if (null != locationCallBack) {
                locationCallBack.onLocationResponse(null);
            }
            return;
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi
                (LocationServices.API).build();

        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setFastestInterval(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMESSION);

            return;
        }
        if (isGPSEnabled() && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            if (null != locationCallBack) {
                locationCallBack.onLocationResponse(null);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (null != locationCallBack) {
            locationCallBack.onLocationResponse(location);
            removeLocationUpdates();

        }
        viewModel.getLocationModel().setLongitude(location.getLongitude());
        viewModel.getLocationModel().setLatitude(location.getLatitude());

        setMarkerOnMap();
    }

    public void removeLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private Boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
