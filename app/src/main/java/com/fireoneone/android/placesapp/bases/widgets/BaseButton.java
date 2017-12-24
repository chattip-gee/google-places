package com.fireoneone.android.placesapp.bases.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.helpers.FontHelper;
import com.fireoneone.android.placesapp.helpers.TranslationHelper;

public class BaseButton extends android.support.v7.widget.AppCompatButton implements FontHelper.CustomFontWidget {

    public BaseButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        }

        FontHelper.FontAttributes fa = new FontHelper.FontAttributes(context, attrs);
        fa.setDefaultFontSizePercent(getFontSizePercent());
        FontHelper.setFont(this, fa);
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackgroundDrawable(background);
        } else {
            super.setBackground(background);
        }
    }

    public String getDefaultFont() {
        return TranslationHelper.get(getResources(), R.string.default_font_button);
    }

    public int getFontSizePercent() {
        return getResources().getInteger(R.integer.font_size_percent_text_view);
    }

    @Override
    public void setCustomTypeface(Typeface typeface, int style, float fontSizeFactor) {
        this.setTypeface(typeface, style);
        this.setTextSize(0, this.getTextSize() * fontSizeFactor);
    }
}
