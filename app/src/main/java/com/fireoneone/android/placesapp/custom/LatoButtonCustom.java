package com.fireoneone.android.placesapp.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Chattip Soontaku.
 */

public class LatoButtonCustom extends Button {

    private Context mContext;

    public LatoButtonCustom(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LatoButtonCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LatoButtonCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LatoButtonCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.otf");
            setTypeface(tf, 0);
        }

    }
}
