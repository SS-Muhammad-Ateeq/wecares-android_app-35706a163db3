package com.wecare.android.ui.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.MyLocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.IOException;
import java.util.Locale;

public class MapLocationPickerActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private TextView resutText;
    private Button doneBtn;
    LocationModel locationModel;
    LocationModel currentLocationModel;
    private MyLocationManager myLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT < 22)
            setStatusBarTranslucent(false);
        else
            setStatusBarTranslucent(true);

        Intent intent = this.getIntent();

        if (intent.getExtras() != null && intent.getExtras().containsKey(AppConstants.ARGS_KEY_LOCATION_OBJECT)) {
            currentLocationModel = intent.getExtras().getParcelable(AppConstants.ARGS_KEY_LOCATION_OBJECT);
        }
        locationModel = new LocationModel();

        myLocationManager = new MyLocationManager(MapLocationPickerActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location_picker);
        resutText = (TextView) findViewById(R.id.dragg_result);
        doneBtn = (Button) findViewById(R.id.doneButton);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationModel.setType(AppConstants.SPECIFIC_LOCATION_LOCATION);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(AppConstants.ARGS_KEY_LOCATION_OBJECT, locationModel);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        configureCameraIdle();

    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                locationModel.setLongitude(latLng.longitude);
                locationModel.setLatitude(latLng.latitude);

//                Geocoder geocoder = new Geocoder(MapLocationPickerActivity.this);

//                try {
//                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    if (addressList != null && addressList.size() > 0) {
//                        String locality = addressList.get(0).getAddressLine(0);
//                        String country = addressList.get(0).getCountryName();
//                        if (locality != null && country != null) {
//                            resutText.setText(String.format("%s  %s", locality, country));
//                            locationModel.setName(String.format("%s  %s", locality, country));
//                        }
//                        locationModel.setLongitude(addressList.get(0).getLongitude());
//                        locationModel.setLatitude(addressList.get(0).getLatitude());
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        };
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        mMap.setOnMarkerDragListener(this);

        //request location permission and show button.
        enableMapLocationButton();

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnCameraIdleListener(onCameraIdleListener);

        if (currentLocationModel != null && (currentLocationModel.getLongitude() != null || currentLocationModel.getLatitude() != null)) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocationModel.getLatitude(), currentLocationModel.getLongitude()), 14.0f));
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {
//            mMap.setMinZoomPreference(15);

            LocationManager locationManager = (LocationManager) MapLocationPickerActivity.this.getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                DialogFactory.createReactDialog(MapLocationPickerActivity.this, getString(R.string.location),
                        getString(R.string.gps_access_denied), getString(R.string.location_settings), getString(R.string.cancel), null,
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(myIntent, AppConstants.REQUEST_LOCATION_SETTING_RESULT);
                            }
                        }, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                onMapLocationButtonClicked();
                            }
                        });
            } else {
                onMapLocationButtonClicked();
            }

            return false;
        }
    };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener = new GoogleMap.OnMyLocationClickListener() {
        @Override
        public void onMyLocationClick(@NonNull Location location) {

            mMap.setMinZoomPreference(12);

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(new LatLng(location.getLatitude(), location.getLongitude()));

            circleOptions.radius(200);
            circleOptions.fillColor(Color.RED);
            circleOptions.strokeWidth(6);

            mMap.addCircle(circleOptions);
        }
    };


    private void enableMapLocationButton() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mapFragment != null && mMap != null) {
                mMap.setMyLocationEnabled(true);

                View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

//                // position on top right
//                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                rlp.setMargins(0, 180, 180, 0);

                // position on right bottom
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                rlp.setMargins(0, 0, 30, 600);

                if (currentLocationModel == null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationButton.performClick();
                        }
                    }, 1000);
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.REQUEST_LOCATION_PERMISSION);
        }
    }

    private void onMapLocationButtonClicked() {
        if (ContextCompat.checkSelfPermission(MapLocationPickerActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MapLocationPickerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (currentLocationModel != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocationModel.getLatitude(), currentLocationModel.getLongitude()), 14.0f));
                locationModel.setLongitude(currentLocationModel.getLongitude());
                locationModel.setLatitude(currentLocationModel.getLatitude());
            } else {
                getMyCurrentLocation();
            }
        } else {
            ActivityCompat.requestPermissions(MapLocationPickerActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.REQUEST_LOCATION_PERMISSION_BUTTON);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.REQUEST_LOCATION_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                enableMapLocationButton();
            }
        } else if (requestCode == AppConstants.REQUEST_LOCATION_PERMISSION_BUTTON) {
            onMapLocationButtonClicked();
        } else if (requestCode == AppConstants.REQUEST_LOCATION_SETTING_RESULT) {
            onMapLocationButtonClicked();
        }
    }

    private void getMyCurrentLocation() {
        if (currentLocationModel == null) {
            //.get location and optional request offer
            myLocationManager.getCurrentLocation(new MyLocationManager.MyLocationListener() {
                @Override
                public void onLocationReceived(Location location) {
                    if (location != null && mMap != null) {
                        Log.d("My Location is", location.getLatitude() + " " + location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14.0f));
                    }
                }
            });
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        LatLng latLng = marker.getPosition();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            Toast.makeText(this,address.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng latLng = marker.getPosition();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            Toast.makeText(this,address.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng latLng = marker.getPosition();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            Toast.makeText(this,address.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
