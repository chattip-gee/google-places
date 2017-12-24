package com.fireoneone.android.placesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fireoneone.android.placesapp.FireOneOneSDK;
import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.BaseActivity;
import com.fireoneone.android.placesapp.fragments.GoogleMapFragment;
import com.fireoneone.android.placesapp.utils.Constant;

public class GoogleMapActivity extends BaseActivity {

    public interface GoogleMapActivityListener {
        void userBackPress();
    }

    public static void present(Context context, String selectedPlace, Integer position, GoogleMapActivityListener listener) {
        FireOneOneSDK.instance().setGoogleMapActivityListener(listener);

        Intent intent = new Intent();
        intent.setClass(context, GoogleMapActivity.class);
        intent.putExtra(Constant.BUNDLE_SELECTED_PLACE, selectedPlace);
        intent.putExtra(Constant.BUNDLE_PLACE_ID, position);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomActionBar(getLayoutInflater().inflate(R.layout.actionbar_with_back, null, false));
        setActionbarTitle(getResources().getString(R.string.title_select_location));

        GoogleMapFragment googleMapFragment = new GoogleMapFragment();
        googleMapFragment.setArguments(getIntent().getExtras());
        replaceFragment(R.id.fragment_container, googleMapFragment);
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (FireOneOneSDK.instance().getGoogleMapActivityListener() != null) {
            FireOneOneSDK.instance().getGoogleMapActivityListener().userBackPress();
        }

        super.onBackPressed();
    }
}
