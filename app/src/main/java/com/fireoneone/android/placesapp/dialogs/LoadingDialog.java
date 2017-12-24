package com.fireoneone.android.placesapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.fireoneone.android.placesapp.R;

/**
 * Created by Chattip Soontaku.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}

