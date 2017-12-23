package com.fireoneone.android.placesapp.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.fireoneone.android.placesapp.managers.Contextor;
import com.fireoneone.android.placesapp.stores.SharedPreference;

import java.util.Locale;

public class TranslationHelper {

    private static TranslationHelper translationHelperInstance;
    private static Context context;
    private static SharedPreference sharedPreference;

    private TranslationHelper() {
        sharedPreference = new SharedPreference(Contextor.getInstance().getContext());
    }

    public static TranslationHelper getInstance() {
        if (translationHelperInstance == null)
            translationHelperInstance = new TranslationHelper();
        return translationHelperInstance;
    }

    public static void init(Context _context) {
        context = _context;
    }

    public static String get(int id) {
        return get(context, id);
    }

    public static String get(Context context, int id) {
        return context.getString(id);
    }

    public static String get(Resources resources, int id) {
        return resources.getString(id);
    }

    public static String get(int id, Object... args) {
        return get(context, id, args);
    }

    public static String get(Context context, int id, Object... args) {
        return context.getString(id, args);
    }

    public void initLanguage(Context context) {
        updateLanguage(context, getDeviceLanguage());
    }

    public void setLanguage(Context context, String language) {
        sharedPreference.setLanguagePreference(language);
        updateLanguage(context, language);
    }

    public String getLanguage() {
        return sharedPreference.getLanguagePreference();
    }

    private static void updateLanguage(Context context, String language) {
        String languageToLoad = language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static String getDeviceLanguage() {
        String localLanguageDevice = Locale.getDefault().getLanguage();
        return localLanguageDevice;
    }
}
