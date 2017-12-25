package com.fireoneone.android.placesapp.apis;

import com.fireoneone.android.placesapp.interfaces.CallbackPlaceDetails;
import com.fireoneone.android.placesapp.interfaces.CallbackPlaceSearch;
import com.fireoneone.android.placesapp.model.google_service.PlaceDetailsBaseModel;
import com.fireoneone.android.placesapp.model.google_service.PlaceSearchBaseModel;
import com.fireoneone.android.placesapp.utils.Constant;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiManager {
    public static final String API_KEY = "AIzaSyDp_JYlA32zevNDOv2GzVIXWxsY8Vo2amA";

    public void getPlaces(String location, int radius, final CallbackPlaceSearch callbackPlaceSearch) {
        Map<String, String> mapStr = new HashMap<>();
        mapStr.put(Constant.KEY, API_KEY);
        mapStr.put(Constant.LOCATION, location);

        Map<String, Integer> mapInt = new HashMap<>();
        mapInt.put(Constant.RADIUS, radius);

        Observable<PlaceSearchBaseModel> observable = new ConnectionManager.Builder()
                .setMappingStr(mapStr)
                .setMappingInt(mapInt)
                .build()
                .initGetPlaces();

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PlaceSearchBaseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackPlaceSearch.onApiError(e.getMessage());
                    }

                    @Override
                    public void onNext(PlaceSearchBaseModel placeSearchBaseModel) {
                        callbackPlaceSearch.onApiSuccess(placeSearchBaseModel);
                    }
                });
    }

    public void getPlaceDetails(String placeId, final CallbackPlaceDetails callbackPlaceDetails) {
        Map<String, String> mapStr = new HashMap<>();
        mapStr.put(Constant.KEY, API_KEY);
        mapStr.put(Constant.PLACEID, placeId);

        Observable<PlaceDetailsBaseModel> observable = new ConnectionManager.Builder()
                .setMappingStr(mapStr)
                .build()
                .initGetPlaceDetails();

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PlaceDetailsBaseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackPlaceDetails.onApiError(e.getMessage());
                    }

                    @Override
                    public void onNext(PlaceDetailsBaseModel placeDetailsBaseModel) {
                        callbackPlaceDetails.onApiSuccess(placeDetailsBaseModel);
                    }
                });
    }
}
