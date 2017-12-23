package com.fireoneone.android.placesapp.fragments;

import android.os.Bundle;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.adapters.MapBalloonAdapter;
import com.fireoneone.android.placesapp.bases.BaseFragment;
import com.fireoneone.android.placesapp.controller.PlacesController;
import com.fireoneone.android.placesapp.databinding.FragmentGoogleMapBinding;
import com.fireoneone.android.placesapp.managers.Contextor;
import com.fireoneone.android.placesapp.model.PlaceItem;
import com.fireoneone.android.placesapp.utils.Constant;
import com.fireoneone.android.placesapp.utils.Validator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GoogleMapFragment extends BaseFragment<FragmentGoogleMapBinding> implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    public static final Double LATITUDE = 13.7244416;
    public static final Double LONGITUDE = 100.3529084;
    public static final LatLng BANGKOK = new LatLng(LATITUDE, LONGITUDE);
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    MarkerOptions markerOptions;
    private GoogleMap mMap;

    @Override
    protected void afterLoadContentView(View view, Bundle savedInstanceState) {
        super.afterLoadContentView(view, savedInstanceState);
        initMap(savedInstanceState);
    }

    @Override
    public int getResourceLayout() {
        return R.layout.fragment_google_map;
    }

    public void initMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        binding.mapviewMap.onCreate(mapViewBundle);
        binding.mapviewMap.getMapAsync(this);

        MapsInitializer.initialize(Contextor.getInstance().getContext());
        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        if (Validator.isNullOrEmpty(getBundleSelectedPlace())) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BANGKOK, 10));
        } else {
            double lat = PlacesController.getInstance().getPlacesList().get(getBundlePlaceId()).getLat();
            double lng = PlacesController.getInstance().getPlacesList().get(getBundlePlaceId()).getLng();
            LatLng nearby = new LatLng(lat, lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearby, 20));
        }

        doMarker();
        setupInfoWindowClickListener();
    }

    private Integer getBundlePlaceId() {
        return getArguments().getInt(Constant.BUNDLE_PLACE_ID);
    }

    private String getBundleSelectedPlace() {
        return getArguments().getString(Constant.BUNDLE_SELECTED_PLACE);
    }

    private void setupInfoWindowClickListener() {
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                getActivity().onBackPressed();
            }
        });
    }

    private void doMarker() {
        if (Validator.isNullOrEmpty(getBundleSelectedPlace())) {
            for (PlaceItem placeItem : PlacesController.getInstance().getPlacesList()) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(placeItem.getLat(), placeItem.getLng()))
                        .title(placeItem.getName())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
            }
        } else {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(PlacesController.getInstance().getPlacesList().get(getBundlePlaceId()).getLat(),
                            PlacesController.getInstance().getPlacesList().get(getBundlePlaceId()).getLng()))
                    .title(PlacesController.getInstance().getPlacesList().get(getBundlePlaceId()).getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (Validator.isNullOrEmpty(getBundleSelectedPlace())) {
            mMap.setInfoWindowAdapter(new MapBalloonAdapter(Contextor.getInstance().getContext(), getActivity().getLayoutInflater(), PlacesController.getInstance().getPlacesList(), null));
        } else {
            mMap.setInfoWindowAdapter(new MapBalloonAdapter(Contextor.getInstance().getContext(), getActivity().getLayoutInflater(), PlacesController.getInstance().getPlacesList(), getBundlePlaceId()));
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapviewMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapviewMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapviewMap.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        binding.mapviewMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapviewMap.onLowMemory();
    }
}