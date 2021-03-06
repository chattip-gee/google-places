package com.fireoneone.android.placesapp.managers;

import android.content.Context;

/**
 * Created by Chattip Soontaku.
 */

public class Contextor {
    private static Contextor instance;
    private Context mContext;

    private Contextor() {

    }

    public static Contextor getInstance() {
        if (instance == null)
            instance = new Contextor();
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
