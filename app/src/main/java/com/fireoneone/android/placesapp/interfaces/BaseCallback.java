package com.fireoneone.android.placesapp.interfaces;

/**
 * Created by Sloth on 12/23/2017.
 */

public interface BaseCallback {
    void onApiError(String errorMessage);

    void onApiSuccess(String success);
}
