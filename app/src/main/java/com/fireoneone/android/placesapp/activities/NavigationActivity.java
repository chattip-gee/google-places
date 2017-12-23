package com.fireoneone.android.placesapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseActivity;
import com.fireoneone.android.placesapp.fragments.NavigationFragment;
import com.fireoneone.android.placesapp.helpers.types.StatusCodeType;
import com.fireoneone.android.placesapp.interfaces.CallbackPlaces;
import com.fireoneone.android.placesapp.stores.realms.PlaceItemRealmManager;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class NavigationActivity extends BaseActivity {
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

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

            }

            @Override
            public void onApiSuccess(PlaceLikelihoodBufferResponse likelyPlaces) {
                replaceFragment(R.id.fragment_container, new NavigationFragment());
            }
        });
    }

    private void findPlacesNearBy(final CallbackPlaces callbackPlaces) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, StatusCodeType.OK);
            return;
        }

        Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                addPlacesNearBy(likelyPlaces);
                likelyPlaces.release();
                callbackPlaces.onApiSuccess(likelyPlaces);

            }
        });
    }

    private void addPlacesNearBy(PlaceLikelihoodBufferResponse likelyPlaces) {
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            String strURL = "";
            if (placeLikelihood.getPlace().getWebsiteUri() != null) {
                strURL = placeLikelihood.getPlace().getWebsiteUri().toString();
            }

            PlaceItemRealmManager.getInstance().addPlaceItem(
                    placeLikelihood.getPlace().getName().toString(),
                    placeLikelihood.getPlace().getAddress().toString(),
                    strURL,
                    placeLikelihood.getPlace().getLatLng().latitude,
                    placeLikelihood.getPlace().getLatLng().longitude);
        }
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

                        }

                        @Override
                        public void onApiSuccess(PlaceLikelihoodBufferResponse likelyPlaces) {
                            replaceFragment(R.id.fragment_container, new NavigationFragment());
                        }
                    });
                }
            }
        }
    }
}
