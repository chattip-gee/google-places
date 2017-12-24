package com.fireoneone.android.placesapp.interfaces;

import com.fireoneone.android.placesapp.model.google_service.PlaceSearchBaseModel;

/**
 * Created by Sloth on 12/23/2017.
 */

public interface CallbackPlaceSearch {
    void onApiError(String errorMessage);

    void onApiSuccess(PlaceSearchBaseModel placeSearchBaseModel);
}
