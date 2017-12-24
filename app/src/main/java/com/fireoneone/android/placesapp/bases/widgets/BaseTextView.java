package com.fireoneone.android.placesapp.bases.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.helpers.FontHelper;
import com.fireoneone.android.placesapp.helpers.TranslationHelper;

public class BaseTextView extends android.support.v7.widget.AppCompatTextView implements FontHelper.CustomFontWidget {

    public BaseTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drawable), null, null, null);
    }

    public boolean isEmpty() {
        String ed_text = this.getText().toString().trim();
        return ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("");
    }

    public String getDefaultFont() {
        return TranslationHelper.get(getResources(), R.string.default_font_text_view);
    }

    public int getFontSizePercent() {
        return getResources().getInteger(R.integer.font_size_percent_text_view);
    }

    @Override
    public void setCustomTypeface(Typeface typeface, int style, float fontSizeFactor) {
        setTypeface(typeface, style);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize() * fontSizeFactor);
    }
}
