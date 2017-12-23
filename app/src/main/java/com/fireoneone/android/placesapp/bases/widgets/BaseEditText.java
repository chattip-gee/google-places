package com.fireoneone.android.placesapp.bases.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.helpers.FontHelper;
import com.fireoneone.android.placesapp.helpers.TranslationHelper;

public class BaseEditText extends android.support.v7.widget.AppCompatEditText implements FontHelper.CustomFontWidget {

    public BaseEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public String getDefaultFont() {
        return TranslationHelper.get(getResources(), R.string.default_font_edit_text);
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
