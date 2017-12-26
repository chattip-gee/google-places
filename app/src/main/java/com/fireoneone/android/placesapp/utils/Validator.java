package com.fireoneone.android.placesapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.util.Patterns;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Validator {
    private static final String TAG = "Validator";
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";\',./<>?";
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public Validator() {
    }

    public static boolean isValid(String s) {
        return s != null && !s.trim().equals("") && !s.trim().equals("null");
    }

    public static boolean isValid(Editable s) {
        return s != null;
    }

    public static boolean isValid(Integer i) {
        return i != null;
    }

    public static boolean isValid(Boolean b) {
        return b != null;
    }

    public static boolean isValid(Long l) {
        return l != null;
    }

    public static boolean isValid(Float f) {
        return f != null;
    }

    public static boolean isValid(Objects o) {
        return o != null;
    }

    public static boolean isValid(Double d) {
        return d != null;
    }

    public static boolean isValid(List items) {
        return items != null && items.size() > 0;
    }

    public static boolean isValidEmail(String email) {
        return isValid(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public static boolean isDouble(String s) {
        return isValid(s) && DOUBLE_PATTERN.matcher(s).matches();
    }

    public static boolean isValidLenght(String value, int maxValue, int minValue) {
        return (maxValue == 0 || value.length() <= maxValue) && (minValue == 0 || value.length() >= minValue);
    }

    public static boolean isValidDigit(String s) {
        return isValid(s) && s.length() == 1 && isNumeric(s);
    }

    public static boolean isNumeric(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isValidOrEmpty(String string) {
        return string != null && !string.equals("") && !string.equals("null");
    }

    public static boolean isValidMobile(String number) {
        return Patterns.PHONE.matcher(number).matches();
    }

    public static String validateUrl(String url) {
        if (url != null) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
        } else {
            url = "http://";
        }

        return url;
    }

    public static void openBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(validateUrl(url)));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    public static boolean isSmallScreen(Context c) {
        return (c.getResources().getConfiguration().screenLayout & 15) == 1;
    }

    public static boolean isNormalScreen(Context c) {
        return (c.getResources().getConfiguration().screenLayout & 15) == 2;
    }

    public static boolean isLandscape(Context c) {
        return c.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.length() == 0;
    }

    public static boolean isNullOrEmpty(Integer input) {
        return input == null || input == 0;
    }

    public static String getValidEmptyValue(String input) {
        if (input == null || input.length() == 0) {
            return "-";
        }

        return input;
    }
}
