package com.fireoneone.android.placesapp.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Chattip Soontaku.
 */

public class LatoHairlineTextViewCustom extends TextView {

    private Context mContext;

    public LatoHairlineTextViewCustom(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LatoHairlineTextViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LatoHairlineTextViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LatoHairlineTextViewCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Hairline.otf");
            setTypeface(tf, 0);
        }

    }
}
