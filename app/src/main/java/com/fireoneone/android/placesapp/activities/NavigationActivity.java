package com.fireoneone.android.placesapp.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseActivity;
import com.fireoneone.android.placesapp.controller.FavoritesController;
import com.fireoneone.android.placesapp.controller.PlacesController;
import com.fireoneone.android.placesapp.fragments.NavigationFragment;
import com.fireoneone.android.placesapp.helpers.NotificationHelper;
import com.fireoneone.android.placesapp.helpers.types.StatusCodeType;
import com.fireoneone.android.placesapp.interfaces.CallbackPlaces;
import com.fireoneone.android.placesapp.managers.FusedLocationSingletonManager;
import com.fireoneone.android.placesapp.managers.LocationManager;
import com.fireoneone.android.placesapp.model.PlaceItem;
import com.fireoneone.android.placesapp.stores.realms.PlaceItemRealmManager;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.fireoneone.android.placesapp.utils.Constant.INTENT_FILTER_LOCATION_UPDATE;
import static com.fireoneone.android.placesapp.utils.Constant.LBM_EVENT_LOCATION_UPDATE;

public class NavigationActivity extends BaseActivity {
    private static final int METER = 100;
    protected Location mCurrentLocation;
    protected Location mPrevLocation;
    protected double mLatitude = 0;
    protected double mLongitude = 0;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    private BroadcastReceiver mLocationUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Location location = (Location) intent.getParcelableExtra(LBM_EVENT_LOCATION_UPDATE);
                mCurrentLocation = location;
                updateLocation();
            } catch (Exception e) {

            }
        }
    };

    private void updateLocation() {
        if (mCurrentLocation != null) {
            mLatitude = mCurrentLocation.getLatitude();
            mLongitude = mCurrentLocation.getLongitude();
            String fmLocation = String.format("%s,%s", mLatitude, mLongitude);
            LocationManager.getInstance().setmLocation(fmLocation);

            if (mPrevLocation == null) {
                mPrevLocation = mCurrentLocation;
            } else {
                for (PlaceItem placeItem : PlacesController.getInstance().getPlacesList()) {
                    if (FavoritesController.getInstance().getFavByObject(placeItem).isFavorite()) {
                        mPrevLocation.setLatitude(placeItem.getLat());
                        mPrevLocation.setLongitude(placeItem.getLng());
                        float distance = mCurrentLocation.distanceTo(mPrevLocation);
                        if (distance > METER) {
                            NotificationHelper.notificationMessage(getBaseContext(),
                                    getResources().getString(R.string.app_name),
                                    getResources().getString(R.string.you_approached_favorite_places, placeItem.getName()),
                                    placeItem.getId());
                            mPrevLocation = mCurrentLocation;
                        }
                    }
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidget();
        initEvent();
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
    }

    private void initWidget() {
        setupData();
    }

    private void setupData() {
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
    }

    private void initEvent() {
        findPlacesNearBy(new CallbackPlaces() {
            @Override
            public void onApiError(String errorMessage) {
                hideLoadingDialog();
            }

            @Override
            public void onApiSuccess(PlaceLikelihoodBufferResponse likelyPlaces) {
                hideLoadingDialog();
                addPlacesNearBy(likelyPlaces);
                likelyPlaces.release();
            }
        });
    }

    private void findPlacesNearBy(final CallbackPlaces callbackPlaces) {
        if (isConnect()) {
            displayLoadingDialog();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, StatusCodeType.OK);
                return;
            }

            Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    callbackPlaces.onApiSuccess(likelyPlaces);

                }
            });
        } else {
            replaceFragment(R.id.fragment_container, new NavigationFragment());
        }
    }

    private void addPlacesNearBy(PlaceLikelihoodBufferResponse likelyPlaces) {
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            String strURL = "";
            if (placeLikelihood.getPlace().getWebsiteUri() != null) {
                strURL = placeLikelihood.getPlace().getWebsiteUri().toString();
            }

            PlaceItemRealmManager.getInstance().addPlaceItem(
                    placeLikelihood.getPlace().getName().toString(),
                    placeLikelihood.getPlace().getId(),
                    placeLikelihood.getPlace().getAddress().toString(),
                    strURL,
                    placeLikelihood.getPlace().getLatLng().latitude,
                    placeLikelihood.getPlace().getLatLng().longitude);
        }

        replaceFragment(R.id.fragment_container, new NavigationFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case StatusCodeType.OK: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findPlacesNearBy(new CallbackPlaces() {
                        @Override
                        public void onApiError(String errorMessage) {
                            hideLoadingDialog();
                        }

                        @Override
                        public void onApiSuccess(PlaceLikelihoodBufferResponse likelyPlaces) {
                            hideLoadingDialog();
                            addPlacesNearBy(likelyPlaces);
                            likelyPlaces.release();
                        }
                    });
                } else {
                    hideLoadingDialog();
                    replaceFragment(R.id.fragment_container, new NavigationFragment());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FusedLocationSingletonManager.getInstance().startLocationUpdates();
        LocalBroadcastManager.getInstance(NavigationActivity.this).registerReceiver(mLocationUpdated, new IntentFilter(INTENT_FILTER_LOCATION_UPDATE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        FusedLocationSingletonManager.getInstance().startLocationUpdates();
        LocalBroadcastManager.getInstance(NavigationActivity.this).registerReceiver(mLocationUpdated, new IntentFilter(INTENT_FILTER_LOCATION_UPDATE));
    }
}
