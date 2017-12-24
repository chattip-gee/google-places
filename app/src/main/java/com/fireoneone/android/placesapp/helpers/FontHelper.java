package com.fireoneone.android.placesapp.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.fireoneone.android.placesapp.R;

public class FontHelper {

    public static interface CustomFontWidget {
        public void setCustomTypeface(Typeface typeface, int style, float fontSizeFactor);
    }

    public static void setFont(CustomFontWidget view, FontAttributes attrs) {
        try {
            String font = attrs.getFontName();
            int fontSizePercent = attrs.fontSizePercent;

            if (fontSizePercent <= 0) {
                fontSizePercent = attrs.defaultFontSizePercent;
            }

            if (font == null || fontSizePercent == 0) {
                return;
            }

            if (!font.startsWith("fonts/")) {
                font = "fonts/" + font;
            }

            Typeface typeface = Typeface.createFromAsset(attrs.context.getAssets(), font);

            view.setCustomTypeface(typeface, Typeface.NORMAL, fontSizePercent / 100.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class FontAttributes {
        protected Context context;
        protected String fontName = null;
        protected String defaultFontName = null;
        protected int fontSizePercent = 100;
        protected int defaultFontSizePercent = 0;

        public FontAttributes(Context context, String fontName) {
            this.context = context;
            this.fontName = fontName;
        }

        public FontAttributes(Context context, AttributeSet attrs) {
            this.context = context;

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontAttributes);
            fontName = a.getString(R.styleable.FontAttributes_customFontName);
            fontSizePercent = a.getInteger(R.styleable.FontAttributes_fontSizePercent, 100);
            a.recycle();
        }

        public String getFontName() {
            if (fontName == null) {
                return defaultFontName;
            }

            return fontName;
        }

        public FontAttributes setDefaultFontName(String defaultFontName) {
            this.defaultFontName = defaultFontName;

            return this;
        }

        public FontAttributes setDefaultFontSizePercent(int defaultFontSizePercent) {
            this.defaultFontSizePercent = defaultFontSizePercent;

            return this;
        }
    }
}
