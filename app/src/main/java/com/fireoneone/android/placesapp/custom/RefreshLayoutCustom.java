package com.fireoneone.android.placesapp.custom;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.fireoneone.android.placesapp.R;

/**
 * Created by Chattip Soontaku.
 */

public class RefreshLayoutCustom extends SwipeRefreshLayout {

    public RefreshLayoutCustom(Context context) {
        super(context);
        init();
    }

    public RefreshLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeResources(R.color.md_cyan_800);
    }

    public void startRefresh() {
        setRefreshing(true);
    }
}
