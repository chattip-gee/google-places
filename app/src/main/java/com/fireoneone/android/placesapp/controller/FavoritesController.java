package com.fireoneone.android.placesapp.controller;

import com.fireoneone.android.placesapp.model.FavoriteItem;
import com.fireoneone.android.placesapp.model.PlaceItem;
import com.fireoneone.android.placesapp.stores.realms.FavoriteItemRealmManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloth on 12/23/2017.
 */

public class FavoritesController {
    static List<FavoriteItem> favoriteItems;
    private static FavoritesController instance;
    private List<FavoriteItem> favList = new ArrayList<>();

    public static FavoritesController getInstance() {
        if (instance == null) {
            instance = new FavoritesController();

            favoriteItems = FavoriteItemRealmManager.getInstance().getFavoriteItems();

            for (int i = 0; i < favoriteItems.size(); i++) {
                FavoriteItem favoriteItem = new FavoriteItem();
                favoriteItem.setPlaceId(favoriteItems.get(i).getPlaceId());
                favoriteItem.setLat(favoriteItems.get(i).getLat());
                favoriteItem.setLng(favoriteItems.get(i).getLng());
                favoriteItem.setFavorite(favoriteItems.get(i).isFavorite());
                instance.favList.add(favoriteItem);
            }
        }
        return instance;
    }

    public List<FavoriteItem> getFavList() {
        return favList;
    }

    public FavoriteItem getFavByObject(PlaceItem placeItem) {
        return FavoriteItemRealmManager.getInstance().getFavoriteItem(placeItem);
    }
}