package com.fireoneone.android.placesapp;

import com.fireoneone.android.placesapp.activities.GoogleMapActivity;
import com.fireoneone.android.placesapp.apis.ApiConstant;
import com.fireoneone.android.placesapp.helpers.types.GoogleMapType;

/**
 * Created by Sloth on 12/23/2017.
 */

public class FireOneOneSDK {

    private static FireOneOneSDK instance;
    private FireOneOneSDKListener listener;
    private static final String DEV_BASE_URL = "https://maps.googleapis.com/maps/";
    private static final String STAGING_BASE_URL = "https://maps.googleapis.com/maps/";
    private static final String PRODUCTION_BASE_URL = "https://maps.googleapis.com/maps/";

    private GoogleMapActivity.GoogleMapActivityListener googleMapActivityListener;

    public interface FireOneOneSDKListener {
        void onError(Throwable e);
    }

    public static FireOneOneSDK instance() {
        if (instance == null) {
            instance = new FireOneOneSDK();
        }

        return instance;
    }

    public static void init(@GoogleMapType.Type String type) {
        switch (type) {
            case GoogleMapType.DEV:
                ApiConstant.init(DEV_BASE_URL);
                break;
            case GoogleMapType.STAGING:
                ApiConstant.init(STAGING_BASE_URL);
                break;
            case GoogleMapType.PROD:
                ApiConstant.init(PRODUCTION_BASE_URL);
                break;
        }
    }

    public FireOneOneSDKListener getListener() {
        return listener;
    }

    public void setListener(FireOneOneSDKListener listener) {
        this.listener = listener;
    }

    public GoogleMapActivity.GoogleMapActivityListener getGoogleMapActivityListener() {
        return googleMapActivityListener;
    }

    public void setGoogleMapActivityListener(GoogleMapActivity.GoogleMapActivityListener googleMapActivityListener) {
        this.googleMapActivityListener = googleMapActivityListener;
    }
}
