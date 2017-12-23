package com.fireoneone.android.placesapp.adapters;

/**
 * Created by chattipsoontaku on 6/30/2017 AD.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.controller.PlacesController;
import com.fireoneone.android.placesapp.custom.LatoBoldTextViewCustom;
import com.fireoneone.android.placesapp.custom.LatoRegularTextViewCustom;
import com.fireoneone.android.placesapp.model.PlaceItem;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import java.util.Collections;
import java.util.List;

public class MapBalloonAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    Context context;
    private LatoBoldTextViewCustom textName;
    private LatoRegularTextViewCustom textAddress;
    private List<PlaceItem> mData = Collections.emptyList();

    public MapBalloonAdapter(Context context, LayoutInflater inflater, List<PlaceItem> data) {
        this.context = context;
        this.inflater = inflater;
        this.mData = data;
    }

    private void initView(View itemView) {
        textName = (LatoBoldTextViewCustom) itemView.findViewById(R.id.text_name);
        textAddress = (LatoRegularTextViewCustom) itemView.findViewById(R.id.text_address);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.widget_balloon_map, null);
        initView(v);
        if (marker != null) {
            for (PlaceItem placeItem : PlacesController.getInstance().getPlacesList()) {
                if (marker.getPosition().latitude == placeItem.getLat() && marker.getPosition().longitude == placeItem.getLng()) {
                    textName.setText(placeItem.getName());
                    textAddress.setText(placeItem.getAddress());
                }
            }
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}