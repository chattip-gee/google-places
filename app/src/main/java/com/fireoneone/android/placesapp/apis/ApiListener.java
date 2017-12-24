package com.fireoneone.android.placesapp.apis;

import com.fireoneone.android.placesapp.model.google_service.PlaceDetailsBaseModel;
import com.fireoneone.android.placesapp.model.google_service.PlaceSearchBaseModel;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Chattip Soontaku.
 */

public interface ApiListener {
    @GET("api/place/nearbysearch/json")
    Observable<PlaceSearchBaseModel> getPlaces(@QueryMap Map<String, String> mapStr, @QueryMap Map<String, Integer> mapInt);

    @GET("api/place/details/json")
    Observable<PlaceDetailsBaseModel> getPlaceDetails(@QueryMap Map<String, String> mapStr);
}