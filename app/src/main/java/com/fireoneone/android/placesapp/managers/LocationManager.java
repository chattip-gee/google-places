package com.fireoneone.android.placesapp.managers;

/**
 * Created by Chattip Soontaku.
 */

public class LocationManager {
    private static LocationManager instance;
    private String mLocation;

    private LocationManager() {

    }

    public static LocationManager getInstance() {
        if (instance == null)
            instance = new LocationManager();
        return instance;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }
}
