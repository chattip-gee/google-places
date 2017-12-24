package com.fireoneone.android.placesapp;

import android.app.Application;

import com.fireoneone.android.placesapp.helpers.TranslationHelper;
import com.fireoneone.android.placesapp.helpers.types.GoogleMapType;
import com.fireoneone.android.placesapp.managers.Contextor;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.plugins.RxJavaHooks;

/**
 * Created by Chattip Soontaku.
 */

public class SettingApplication extends Application {
    private static SettingApplication instance;

    public static SettingApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Contextor.getInstance().init(getApplicationContext());
        FireOneOneSDK.init(GoogleMapType.PROD);
        TranslationHelper.getInstance().initLanguage(this);
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        RxJavaHooks.enableAssemblyTracking();
    }
}
