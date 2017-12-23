package com.fireoneone.android.placesapp.controller;

import com.fireoneone.android.placesapp.model.PlaceItem;
import com.fireoneone.android.placesapp.stores.realms.PlaceItemRealmManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloth on 12/23/2017.
 */

public class PlacesController {
    static List<PlaceItem> placeItems;
    private static PlacesController instance;
    private List<PlaceItem> placesList = new ArrayList<>();

    public static PlacesController getInstance() {
        if (instance == null) {
            instance = new PlacesController();

            placeItems = PlaceItemRealmManager.getInstance().getPlaceItems();

            for (int i = 0; i < placeItems.size(); i++) {
                PlaceItem placeItem = new PlaceItem();
                placeItem.setId(i);
                placeItem.setName(placeItems.get(i).getName());
                placeItem.setAddress(placeItems.get(i).getAddress());
                placeItem.setUrl(placeItems.get(i).getUrl());
                placeItem.setLat(placeItems.get(i).getLat());
                placeItem.setLng(placeItems.get(i).getLng());
                instance.placesList.add(placeItem);
            }
        }
        return instance;
    }

    public List<PlaceItem> getPlacesList() {
        return placesList;
    }
}
