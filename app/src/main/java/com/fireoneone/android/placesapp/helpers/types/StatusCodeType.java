package com.fireoneone.android.placesapp.helpers.types;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Chattip Soontaku.
 */

public class StatusCodeType {
    public static final int ACCEPTED = 202;
    public static final int BAD_REQUEST = 400;
    public static final int CONFLICT = 409;
    public static final int CREATED = 201;
    public static final int FORBIDDEN = 403;
    public static final int GONE = 410;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int NO_CONTENT = 204;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int NOT_FOUND = 404;
    public static final int NOT_MODIFIED = 304;
    public static final int OK = 200;
    public static final int PRECONDITION_FAILED = 412;
    public static final int SEE_OTHER = 303;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int UNAUTHORIZED = 401;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACCEPTED, BAD_REQUEST, CONFLICT, CREATED, FORBIDDEN, GONE, INTERNAL_SERVER_ERROR,
            MOVED_PERMANENTLY, NO_CONTENT, NOT_ACCEPTABLE, NOT_FOUND, NOT_MODIFIED, OK, PRECONDITION_FAILED,
            SEE_OTHER, SERVICE_UNAVAILABLE, TEMPORARY_REDIRECT, UNAUTHORIZED, UNSUPPORTED_MEDIA_TYPE})
    public @interface Type {
    }
}
