package com.fireoneone.android.placesapp.stores.realms;

import com.fireoneone.android.placesapp.model.PlaceItem;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Chattip Soontaku.
 */

public class PlaceItemRealmManager {
    private static PlaceItemRealmManager instance;
    private final Realm realm;
    private Integer index = 0;

    public PlaceItemRealmManager() {
        realm = Realm.getDefaultInstance();
    }

    public static PlaceItemRealmManager getInstance() {
        if (instance == null)
            instance = new PlaceItemRealmManager();

        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(PlaceItem.class);
        realm.commitTransaction();
    }

    public RealmResults<PlaceItem> getPlaceItems() {
        return realm.where(PlaceItem.class).findAll();
    }

    public void addPlaceItem(String name, String address, String url, double lat, double lng) {
        PlaceItem placeItem = new PlaceItem();

        realm.beginTransaction();
        placeItem.setId(index);
        placeItem.setName(name);
        placeItem.setAddress(address);
        placeItem.setUrl(url);
        placeItem.setLat(lat);
        placeItem.setLng(lng);
        realm.insertOrUpdate(placeItem);
        realm.commitTransaction();

        index++;
    }
}