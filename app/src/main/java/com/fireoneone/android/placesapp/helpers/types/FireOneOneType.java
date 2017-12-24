package com.fireoneone.android.placesapp.helpers.types;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Sloth on 12/24/2017.
 */

public class FireOneOneType {
    public static final String DEV = "dev";
    public static final String STAGING = "staging";
    public static final String PROD = "prod";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({DEV, STAGING, PROD})
    public @interface Type {
    }
}
