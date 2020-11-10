package com.wecare.android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.core.app.ActivityCompat;

/**
 * Created by Anas on 1/11/2016.
 * this class handles the Location services and get the Locatio of the device
 */
public class MyLocationManager {

    private Context mContext;
    private Location myLocation = null;
    private AtomicBoolean locationUpdated = new AtomicBoolean(false);
    private long timeout = TimeUnit.SECONDS.toMillis(2);    // 2 Sec
    Handler mHandler = new Handler();


    public interface MyLocationListener {
        public void onLocationReceived(Location location);
    }


    public MyLocationManager(Context context) {
        this.mContext = context;
    }


    public boolean isServiceEnabled() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gps_enabled || network_enabled;
    }


    /**
     * this method retrieve the location of the device using the Network Provider
     */
    public void getCurrentLocation(final MyLocationListener listener) {
        final LocationManager locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            myLocation = location;
        }


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.removeUpdates(this);
                    myLocation = location;
                    locationUpdated.set(true);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 0;
                while (!locationUpdated.get() && time < timeout) {
                    try {
                        Thread.sleep(100);
                        time += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (listener != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onLocationReceived(myLocation);
                        }
                    });
                }
            }
        }).start();
    }
}
