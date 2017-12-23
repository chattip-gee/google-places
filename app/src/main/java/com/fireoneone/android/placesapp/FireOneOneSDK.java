package com.fireoneone.android.placesapp;

import com.fireoneone.android.placesapp.activities.GoogleMapActivity;

/**
 * Created by Sloth on 12/23/2017.
 */

public class FireOneOneSDK {

    private static FireOneOneSDK instance;
    private FireOneOneSDKListener listener;

    private GoogleMapActivity.GoogleMapActivityListener googleMapActivityListener;

    public static FireOneOneSDK instance() {
        if (instance == null) {
            instance = new FireOneOneSDK();
        }

        return instance;
    }

    public interface FireOneOneSDKListener {
        void onError(Throwable e);
    }

    public GoogleMapActivity.GoogleMapActivityListener getGoogleMapActivityListener() {
        return googleMapActivityListener;
    }

    public void setGoogleMapActivityListener(GoogleMapActivity.GoogleMapActivityListener googleMapActivityListener) {
        this.googleMapActivityListener = googleMapActivityListener;
    }
}
