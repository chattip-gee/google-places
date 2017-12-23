package com.fireoneone.android.placesapp.interfaces;

import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;

/**
 * Created by Sloth on 12/23/2017.
 */

public interface CallbackPlaces {
    void onApiError(String errorMessage);

    void onApiSuccess(PlaceLikelihoodBufferResponse likelyPlaces);
}
