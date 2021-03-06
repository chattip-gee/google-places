package com.fireoneone.android.placesapp.bases;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.bases.widgets.BaseTextView;
import com.fireoneone.android.placesapp.dialogs.InfoDialog;
import com.fireoneone.android.placesapp.dialogs.LoadingDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscription;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;
    protected Subscription subscription;
    protected LoadingDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getResourceLayout());
        beforeLoadContentView(savedInstanceState);
        setContentView(getResourceLayout());
        afterLoadContentView(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        super.onStop();
    }

    public abstract int getResourceLayout();

    protected void beforeLoadContentView(Bundle savedInstanceState) {

    }

    protected void afterLoadContentView(Bundle savedInstanceState) {

    }

    //********************************* START COMPACT ACTIVITY ***************************************************************************************************

    public void startCompactActivity(Class clazz) {
        startCompactActivity(clazz, null);
    }

    public void startCompactActivity(Class clazz, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startCompactActivity(intent, extras, null);
    }

    public void startCompactActivity(Intent intent, Bundle extras, Bundle launchOptions) {
        if (extras != null) {
            intent.putExtras(extras);
        }

        ActivityCompat.startActivity(this, intent, launchOptions);
    }

    //********************************* START NEW STACK ACTIVITY *************************************************************************************************

    public void startNewStackActivity(Class clazz) {
        startNewStackActivity(clazz, null);
    }

    public void startNewStackActivity(Class clazz, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startNewStackActivity(intent, extras, null);
    }

    public void startNewStackActivity(Intent intent, Bundle extras, Bundle launchOptions) {
        if (intent == null) {
            return;
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (extras != null) {
            intent.putExtras(extras);
        }

        ActivityCompat.startActivity(this, intent, launchOptions);
    }

    //********************************* REPLACE FRAGMENT **********************************************************************************************************

    protected void replaceFragment(int containerId, Fragment fragment) {
        replaceFragment(containerId, fragment, null, false, 0, 0, false);
    }

    protected void replaceFragment(int containerId, Fragment fragment, boolean replaceAnyway) {
        replaceFragment(containerId, fragment, null, false, 0, 0, replaceAnyway);
    }

    protected void replaceFragment(int containerId, Fragment fragment, String tag, boolean addToBackStack, int enterAnim, int exitAnim, boolean replaceAnyway) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(containerId);

        if (!replaceAnyway) {
            if (currentFragment != null && (currentFragment.getClass().equals(fragment.getClass()))) {
                return;
            }
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (enterAnim != 0 && exitAnim != 0) {
            ft.setCustomAnimations(enterAnim, exitAnim);
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.replace(containerId, fragment, tag);
        ft.commit();
    }

    //********************************* ADD FRAGMENT ***************************************************************************************************************

    protected void addFragment(int containerId, Fragment fragment) {
        addFragment(containerId, fragment, null, false, 0, 0);
    }

    protected void addFragment(int containerId, Fragment fragment, String tag, boolean addToBackStack, int enterAnim, int exitAnim) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (enterAnim != 0 && exitAnim != 0) {
            ft.setCustomAnimations(enterAnim, exitAnim);
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.add(containerId, fragment, tag);
        ft.commit();
    }

    //********************************* REMOVE FRAGMENT ************************************************************************************************************

    protected void removeFragment(int containerId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(containerId);
        removeFragment(fragment);
    }

    protected void removeFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    //********************************* ACTION BAR ******************************************************************************************************************

    private void doTransparentActionBar() {
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            actionBar.setIcon(android.R.color.transparent);
        }
    }

    public void setActionbarTitle(String title) {
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            BaseTextView textView = (BaseTextView) actionBar.getCustomView().findViewById(R.id.text_actionbar_title);
            textView.setText(title);
        }
    }

    public void setActionbarTitle(int titleResourceId) {
        setActionbarTitle(getResources().getString(titleResourceId));
    }

    public void setCustomActionBar(View view) {
        if (getSupportActionBar() == null) {
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(view);
    }

    public void onActionBarBack(View v) {
        onBackPressed();
    }

    //********************************* CHECK NETWORK ******************************************************************************************************************

    public boolean isConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    //********************************* LOADING **********************************************************************************************************************

    public void displayLoadingDialog() {
        displayLoadingDialog(getResources().getString(R.string.global_loading));
    }

    public void displayLoadingDialog(String s) {
        hideLoadingDialog();

        mProgressDialog = new LoadingDialog(this);
        mProgressDialog.show();
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    //********************************* SHOW DIALOG *******************************************************************************************************************

    public void showInfo(String title, String description) {
        InfoDialog infoDialog = InfoDialog.newInstance(title, description);
        infoDialog.setInfoDialogListener(null);
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getSupportFragmentManager(), null);
    }


    public void showNoInternetConnectionError() {
        InfoDialog infoDialog = InfoDialog.newInstance(
                R.string.dialog_no_internet_connection_title,
                R.string.dialog_error_description
        );

        infoDialog.setInfoDialogListener(null
        );
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getSupportFragmentManager(), null);
    }

    public void showNoInternetConnectionError(Throwable e) {
        showNoInternetConnectionError(e, null);
    }

    public void showNoInternetConnectionError(Throwable e, InfoDialog.InfoDialogListener listener) {
        InfoDialog infoDialog = InfoDialog.newInstance(
                R.string.dialog_no_internet_connection_title,
                R.string.dialog_error_description
        );

        infoDialog.setInfoDialogListener(listener);
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getSupportFragmentManager(), null);
    }

    public void showError(Throwable e) {
        showError(e, null);
    }

    public void showError(Throwable e, InfoDialog.InfoDialogListener listener) {
        InfoDialog infoDialog;
        if ((e instanceof SocketTimeoutException || e instanceof UnknownHostException || e instanceof IOException)) {
            infoDialog = InfoDialog.newInstance(
                    R.string.dialog_no_internet_connection_title,
                    R.string.dialog_no_internet_connection_description
            );
        } else {
            infoDialog = InfoDialog.newInstance(
                    R.string.dialog_error_title,
                    e.getLocalizedMessage()
            );
        }

        infoDialog.setInfoDialogListener(listener);
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getSupportFragmentManager(), null);
    }
}
