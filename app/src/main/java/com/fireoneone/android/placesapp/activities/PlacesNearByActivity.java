package com.fireoneone.android.placesapp.activities;

import android.os.Bundle;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseActivity;
import com.fireoneone.android.placesapp.fragments.PlacesNearByFragment;

public class PlacesNearByActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.fragment_container, new PlacesNearByFragment());
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
    }
}
