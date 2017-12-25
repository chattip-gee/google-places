package com.fireoneone.android.placesapp.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.activities.GoogleMapActivity;
import com.fireoneone.android.placesapp.adapters.PlacesViewAdapter;
import com.fireoneone.android.placesapp.apis.ApiManager;
import com.fireoneone.android.placesapp.bases.BaseFragment;
import com.fireoneone.android.placesapp.controller.PlacesController;
import com.fireoneone.android.placesapp.databinding.FragmentPlacesNearbyBinding;
import com.fireoneone.android.placesapp.interfaces.CallbackPlaceDetails;
import com.fireoneone.android.placesapp.interfaces.CallbackPlaceSearch;
import com.fireoneone.android.placesapp.interfaces.NavigationController;
import com.fireoneone.android.placesapp.managers.Contextor;
import com.fireoneone.android.placesapp.managers.LocationManager;
import com.fireoneone.android.placesapp.model.google_service.PlaceDetailsBaseModel;
import com.fireoneone.android.placesapp.model.google_service.PlaceSearchBaseModel;
import com.fireoneone.android.placesapp.model.google_service.place_search.Result;
import com.fireoneone.android.placesapp.stores.realms.PlaceItemRealmManager;
import com.fireoneone.android.placesapp.utils.Constant;
import com.fireoneone.android.placesapp.utils.Validator;

public class PlacesNearByFragment extends BaseFragment<FragmentPlacesNearbyBinding> implements NavigationController {
    static PlacesNearByFragment placesNearByFragment;
    private int numberOfColumns = 1;
    GridLayoutManager mLayoutManager;
    PlacesViewAdapter placesViewAdapter;

    public static PlacesNearByFragment newInstance() {
        if (placesNearByFragment == null) {
            placesNearByFragment = new PlacesNearByFragment();
        }
        return placesNearByFragment;
    }

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
        binding.refreshPage.setColorSchemeColors(getResources().getColor(R.color.standard_app_color));
        setupRecycleView();
        setupAdapter();
    }

    private void setupRecycleView() {
        mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        binding.recycleViewList.setLayoutManager(mLayoutManager);
    }

    private void setupAdapter() {
        placesViewAdapter = new PlacesViewAdapter(Contextor.getInstance().getContext(), PlacesController.getInstance().getPlacesList());
        binding.recycleViewList.setAdapter(placesViewAdapter);

        if (!Validator.isNullOrEmpty(PlacesController.getInstance().getPlacesList().size())) {
            binding.textNoResults.setVisibility(View.GONE);
        } else {
            binding.textNoResults.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
        refreshData();
        setupClickListener();
        setupRefreshPageListener();
    }

    private void setupClickListener() {
        placesViewAdapter.setClickListener(new PlacesViewAdapter.ItemClickListener() {
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

    public void setupRefreshPageListener() {
        binding.refreshPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshPage.setRefreshing(true);
                refreshData();
                getPlacesNearBy();
            }
        });
    }

    private void refreshData() {
        placesViewAdapter.clearAllData();
        placesViewAdapter.notifyDataSetChanged();
    }

    private void getPlacesNearBy() {
        new ApiManager().getPlaces(LocationManager.getInstance().getmLocation(), 100, new CallbackPlaceSearch() {
            @Override
            public void onApiError(String errorMessage) {
                hideLoadingDialog();
                binding.refreshPage.setRefreshing(false);
            }

            @Override
            public void onApiSuccess(PlaceSearchBaseModel placeSearchBaseModel) {
                if (!Validator.isNullOrEmpty(placeSearchBaseModel.getResults().size())) {
                    for (final Result resultItem : placeSearchBaseModel.getResults()) {
                        new ApiManager().getPlaceDetails(resultItem.getPlaceId(), new CallbackPlaceDetails() {
                            @Override
                            public void onApiError(String errorMessage) {
                                hideLoadingDialog();
                                binding.refreshPage.setRefreshing(false);
                            }

                            @Override
                            public void onApiSuccess(PlaceDetailsBaseModel placeDetailsBaseModel) {
                                hideLoadingDialog();
                                binding.refreshPage.setRefreshing(false);

                                PlaceItemRealmManager.getInstance().clearAll();
                                PlaceItemRealmManager.getInstance().addPlaceItem(
                                        placeDetailsBaseModel.getResult().getName(),
                                        resultItem.getPlaceId(),
                                        placeDetailsBaseModel.getResult().getVicinity(),
                                        placeDetailsBaseModel.getResult().getWebsite(),
                                        placeDetailsBaseModel.getResult().getGeometry().getLocation().getLat(),
                                        placeDetailsBaseModel.getResult().getGeometry().getLocation().getLng());
                                placesViewAdapter.notifyDataSetChanged();
                            }
                        });

                        placesViewAdapter.notifyItemChanged(placeSearchBaseModel.getResults().size());
                    }
                } else {
                    hideLoadingDialog();
                    binding.refreshPage.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onPageSelected() {
        refreshData();
    }
}
