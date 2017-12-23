package com.fireoneone.android.placesapp;

import android.app.Application;

import com.fireoneone.android.placesapp.helpers.TranslationHelper;
import com.fireoneone.android.placesapp.managers.Contextor;
import com.fireoneone.android.placesapp.stores.SharedPreference;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Chattip Soontaku.
 */

public class SettingApplication extends Application {
    private static SettingApplication instance;
    private SharedPreference sharedPreference;

    public static SettingApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Contextor.getInstance().init(getApplicationContext());
        sharedPreference = new SharedPreference(Contextor.getInstance().getContext());
        TranslationHelper.getInstance().initLanguage(this);
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
