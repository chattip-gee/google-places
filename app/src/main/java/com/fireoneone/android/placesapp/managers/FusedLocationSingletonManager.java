package com.fireoneone.android.placesapp.managers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.fireoneone.android.placesapp.utils.Constant.INTENT_FILTER_LOCATION_UPDATE;
import static com.fireoneone.android.placesapp.utils.Constant.LBM_EVENT_LOCATION_UPDATE;

/**
 * Created by chattipsoontaku on 7/25/2017 AD.
 */

public class FusedLocationSingletonManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = (60 * 60) * 1000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static FusedLocationSingletonManager mInstance = null;
    protected GoogleApiClient mGoogleApiClient;
    Context context;
    private LocationRequest mLocationRequest;

    public FusedLocationSingletonManager() {
        context = Contextor.getInstance().getContext();
        buildGoogleApiClient();
    }

    public static FusedLocationSingletonManager getInstance() {
        if (null == mInstance) {
            mInstance = new FusedLocationSingletonManager();
        }
        return mInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stopLocationUpdates();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Contextor.getInstance().getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        configRequestLocationUpdate();
    }

    private void configRequestLocationUpdate() {
        mLocationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    }

    @TargetApi(23)
    private void requestLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            );
        } catch (Exception e) {

        }
    }

    public void startLocationUpdates() {
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    public void stopLocationUpdates() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }

    }

    @TargetApi(23)
    public Location getLastLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            startLocationUpdates();
            return null;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (null != mGoogleApiClient) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Intent intent = new Intent(INTENT_FILTER_LOCATION_UPDATE);
            intent.putExtra(LBM_EVENT_LOCATION_UPDATE, location);
            LocalBroadcastManager.getInstance(Contextor.getInstance().getContext()).sendBroadcast(intent);
        }
    }
}
