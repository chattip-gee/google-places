package com.fireoneone.android.placesapp.stores.realms;

import com.fireoneone.android.placesapp.model.FavoriteItem;
import com.fireoneone.android.placesapp.model.PlaceItem;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Chattip Soontaku.
 */

public class FavoriteItemRealmManager {
    private static FavoriteItemRealmManager instance;
    private final Realm realm;

    public FavoriteItemRealmManager() {
        realm = Realm.getDefaultInstance();
    }

    public static FavoriteItemRealmManager getInstance() {
        if (instance == null)
            instance = new FavoriteItemRealmManager();

        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(FavoriteItem.class);
        realm.commitTransaction();
    }

    public RealmResults<FavoriteItem> getFavoriteItems() {
        return realm.where(FavoriteItem.class).findAll();
    }

    public FavoriteItem getFavoriteItem(PlaceItem placeItem) {
        FavoriteItem favoriteItem = realm.where(FavoriteItem.class)
                .equalTo("placeId", placeItem.getPlaceId())
                .equalTo("lat", placeItem.getLat())
                .equalTo("lng", placeItem.getLng())
                .findFirst();

        if (favoriteItem == null) {
            favoriteItem = new FavoriteItem();
        }
        return favoriteItem;
    }

    public void addFavoriteItem(String placeId, double lat, double lng, boolean isFav) {
        FavoriteItem favoriteItem = new FavoriteItem();

        realm.beginTransaction();
        favoriteItem.setPlaceId(placeId);
        favoriteItem.setLat(lat);
        favoriteItem.setLng(lng);
        favoriteItem.setFavorite(isFav);
        realm.insertOrUpdate(favoriteItem);
        realm.commitTransaction();
    }
}