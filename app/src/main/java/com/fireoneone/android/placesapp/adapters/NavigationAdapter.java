package com.fireoneone.android.placesapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.fragments.PlacesNearByFragment;
import com.fireoneone.android.placesapp.managers.Contextor;

/**
 * Created by Sloth on 12/23/2017.
 */

public class NavigationAdapter extends FragmentPagerAdapter {

    public NavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PlacesNearByFragment();
            case 1:
                return new PlacesNearByFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Contextor.getInstance().getContext().getResources().getString(R.string.title_menu1);
            case 1:
                return Contextor.getInstance().getContext().getResources().getString(R.string.title_menu2);
            default:
                return null;
        }
    }
}