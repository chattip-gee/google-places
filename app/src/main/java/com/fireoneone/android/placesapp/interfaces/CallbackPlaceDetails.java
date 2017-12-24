package com.fireoneone.android.placesapp.interfaces;

import com.fireoneone.android.placesapp.model.google_service.PlaceDetailsBaseModel;

/**
 * Created by Sloth on 12/23/2017.
 */

public interface CallbackPlaceDetails {
    void onApiError(String errorMessage);

    void onApiSuccess(PlaceDetailsBaseModel placeDetailsBaseModel);
}
