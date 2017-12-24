package com.fireoneone.android.placesapp.stores;

import android.content.Context;
import android.content.SharedPreferences;

import com.fireoneone.android.placesapp.helpers.TranslationHelper;
import com.fireoneone.android.placesapp.utils.Constant;

/**
 * Created by Chattip Soontaku.
 */

public class SharedPreference {

    private static final String PREF_KEY = "PREF_KEY";
    public static final String PREF_LANGUAGE = "PREF_LANGUAGE";
    private Context mContext;

    public SharedPreference(Context context) {
        this.mContext = context;
    }

    private SharedPreferences.Editor getEditor() {
        return mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getPreference() {
        return mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public boolean isEnglishLanguage() {
        if (getLanguagePreference() == null) {
            return false;
        } else {
            if (getLanguagePreference().equals(Constant.LANGUAGE_EN)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String getLanguagePreference() {
        SharedPreferences preferences = getPreference();
        return preferences.getString(PREF_LANGUAGE, TranslationHelper.getDeviceLanguage());
    }

    public void setLanguagePreference(String language) {
        final SharedPreferences.Editor editor = getEditor();
        editor.putString(PREF_LANGUAGE, language);
        editor.apply();
    }
}
