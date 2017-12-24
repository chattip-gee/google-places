package com.fireoneone.android.placesapp.bases;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public abstract class BaseDialogFragment<T extends ViewDataBinding> extends DialogFragment {

    public static final String TAG = "BaseDialogFragment";

    protected T binding;

    protected boolean noTitle = true;
    protected boolean cancellable = true;
    protected boolean cancelOnTouchOutside = true;
    protected boolean fullscreen = false;

    public BaseDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        beforeLoadContentView(savedInstanceState);
        View root = inflater.inflate(getResourceLayout(), container, false);
        binding = DataBindingUtil.inflate(inflater, getResourceLayout(), container, false);
        afterLoadContentView(root, savedInstanceState);

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (noTitle) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        setCancelable(cancellable);
        setCancelOnTouchOutside(cancelOnTouchOutside);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();

        Dialog dialog = getDialog();
        if (dialog != null && fullscreen) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
    }

    protected abstract int getResourceLayout();

    protected void beforeLoadContentView(Bundle savedInstanceState) {

    }

    protected void afterLoadContentView(View view, Bundle savedInstanceState) {

    }

    protected void initialize() {

    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public void setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        this.cancelOnTouchOutside = cancelOnTouchOutside;
    }

    public void setNoTitle(boolean noTitle) {
        this.noTitle = noTitle;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
}
