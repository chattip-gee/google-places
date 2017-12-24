package com.fireoneone.android.placesapp.apis;

/**
 * Created by Chattip Soontaku.
 */

public class ApiConstant {
    public static final String PLACE_NEARBY_SEARCH_PATH = "place/nearbysearch/json";
    public static final String PLACE_DETAIL_PATH = "place/details/json";

    public static String sBaseURL;

    public static void init(String baseURL) {
        sBaseURL = baseURL;
    }
}
