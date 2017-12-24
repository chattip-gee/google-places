package com.fireoneone.android.placesapp.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.activities.GoogleMapActivity;
import com.fireoneone.android.placesapp.adapters.FavoritePlacesViewAdapter;
import com.fireoneone.android.placesapp.bases.BaseFragment;
import com.fireoneone.android.placesapp.controller.PlacesController;
import com.fireoneone.android.placesapp.databinding.FragmentPlacesNearbyBinding;
import com.fireoneone.android.placesapp.managers.Contextor;
import com.fireoneone.android.placesapp.utils.Constant;

public class FavoriteFragment extends BaseFragment<FragmentPlacesNearbyBinding> {
    private int numberOfColumns = 1;
    GridLayoutManager mLayoutManager;
    FavoritePlacesViewAdapter favoritePlacesViewAdapter;

    @Override
    protected void afterLoadContentView(View view, Bundle savedInstanceState) {
        super.afterLoadContentView(view, savedInstanceState);
        initWidget();
        initEvent();
    }

    @Override
    public int getResourceLayout() {
        return R.layout.fragment_places_nearby;
    }

    private void initWidget() {
        setupRecycleView();
        setupAdapter();
    }

    private void setupRecycleView() {
        mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        binding.recycleViewList.setLayoutManager(mLayoutManager);
    }

    private void setupAdapter() {
        favoritePlacesViewAdapter = new FavoritePlacesViewAdapter(Contextor.getInstance().getContext(), PlacesController.getInstance().getPlacesList());
        binding.recycleViewList.setAdapter(favoritePlacesViewAdapter);
    }

    private void initEvent() {
        refreshData();
        setupClickListener();
    }

    private void setupClickListener() {
        favoritePlacesViewAdapter.setClickListener(new FavoritePlacesViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoogleMapActivity.present(Contextor.getInstance().getContext(), Constant.SELECTED_PLACE_KEY, position, new GoogleMapActivity.GoogleMapActivityListener() {
                    @Override
                    public void userBackPress() {

                    }
                });
            }
        });
    }

    private void refreshData() {
        favoritePlacesViewAdapter.clearAllData();
        favoritePlacesViewAdapter.notifyDataSetChanged();
    }
}
