package com.fireoneone.android.placesapp.fragments;

import android.os.Bundle;
import android.view.View;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.adapters.NavigationAdapter;
import com.fireoneone.android.placesapp.bases.BaseFragment;
import com.fireoneone.android.placesapp.databinding.FragmentNavigationBinding;

/**
 * Created by Sloth on 12/23/2017.
 */

public class NavigationFragment extends BaseFragment<FragmentNavigationBinding> {

    NavigationAdapter navigationAdapter;

    @Override
    protected void afterLoadContentView(View view, Bundle savedInstanceState) {
        super.afterLoadContentView(view, savedInstanceState);
        initWidget();
    }

    private void initWidget() {
        navigationAdapter = new NavigationAdapter(getFragmentManager());
        binding.vpPages.setAdapter(navigationAdapter);
        binding.tblPages.setupWithViewPager(binding.vpPages);
    }

    @Override
    public int getResourceLayout() {
        return R.layout.fragment_navigation;
    }
}
