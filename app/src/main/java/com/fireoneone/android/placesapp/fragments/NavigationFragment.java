package com.fireoneone.android.placesapp.fragments;

import android.os.Bundle;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.activities.GoogleMapActivity;
import com.fireoneone.android.placesapp.adapters.NavigationAdapter;
import com.fireoneone.android.placesapp.bases.BaseFragment;
import com.fireoneone.android.placesapp.databinding.FragmentNavigationBinding;
import com.fireoneone.android.placesapp.managers.Contextor;

/**
 * Created by Sloth on 12/23/2017.
 */

public class NavigationFragment extends BaseFragment<FragmentNavigationBinding> implements View.OnClickListener {

    NavigationAdapter navigationAdapter;

    @Override
    protected void afterLoadContentView(View view, Bundle savedInstanceState) {
        super.afterLoadContentView(view, savedInstanceState);
        initWidget();
        initEvent();
    }

    private void initWidget() {
        navigationAdapter = new NavigationAdapter(getFragmentManager());
        binding.vpPages.setAdapter(navigationAdapter);
        binding.tblPages.setupWithViewPager(binding.vpPages);
    }

    private void initEvent() {
        setupClickListener();
    }

    private void setupClickListener() {
        binding.fab.setOnClickListener(this);
    }

    @Override
    public int getResourceLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            GoogleMapActivity.present(Contextor.getInstance().getContext(), "", null, new GoogleMapActivity.GoogleMapActivityListener() {
                @Override
                public void userBackPress() {

                }
            });
        }
    }
}
