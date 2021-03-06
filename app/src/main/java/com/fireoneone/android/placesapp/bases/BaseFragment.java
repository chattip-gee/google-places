package com.fireoneone.android.placesapp.bases;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.dialogs.InfoDialog;
import com.fireoneone.android.placesapp.dialogs.LoadMoreDialog;
import com.fireoneone.android.placesapp.dialogs.LoadingDialog;

import rx.Subscription;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected T binding;
    protected Subscription subscription;
    protected LoadingDialog mProgressDialog;
    protected LoadMoreDialog mProgressLoadMoreDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        beforeLoadContentView(savedInstanceState);
        View view = inflater.inflate(getResourceLayout(), container, false);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getBaseContext());
        binding = DataBindingUtil.inflate(layoutInflater, getResourceLayout(), container, false);
        afterLoadContentView(view, savedInstanceState);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onStop() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        super.onStop();
    }

    public abstract int getResourceLayout();

    protected void beforeLoadContentView(Bundle savedInstanceState) {

    }

    protected void afterLoadContentView(View view, Bundle savedInstanceState) {

    }

    protected void initialize() {

    }

    //********************************* START COMPACT ACTIVITY ***************************************************************************************************

    public void startCompactActivity(Class clazz) {
        startCompactActivity(clazz, null);
    }

    public void startCompactActivity(Class clazz, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startCompactActivity(intent, extras, null);
    }

    public void startCompactActivity(Intent intent, Bundle extras, Bundle launchOptions) {
        if (extras != null) {
            intent.putExtras(extras);
        }

        ActivityCompat.startActivity(getActivity(), intent, launchOptions);
    }

    //********************************* START NEW STACK ACTIVITY *************************************************************************************************

    public void startNewStackActivity(Class clazz) {
        startNewStackActivity(clazz, null);
    }

    public void startNewStackActivity(Class clazz, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startNewStackActivity(intent, extras, null);
    }

    public void startNewStackActivity(Intent intent, Bundle extras, Bundle launchOptions) {
        if (getActivity() == null || intent == null) {
            return;
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (extras != null) {
            intent.putExtras(extras);
        }

        ActivityCompat.startActivity(getActivity(), intent, launchOptions);
    }

    //********************************* REPLACE FRAGMENT **********************************************************************************************************

    protected void replaceFragment(int containerId, Fragment fragment) {
        replaceFragment(containerId, fragment, null, false, 0, 0);
    }

    protected void replaceFragment(int containerId, Fragment fragment, String tag,
                                   boolean addToBackStack, int enterAnim, int exitAnim) {
        Fragment currentFragment = getChildFragmentManager().findFragmentById(containerId);

        if (currentFragment != null && (currentFragment.getClass().equals(fragment.getClass()))) {
            return;
        }

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

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

    protected void addFragment(int containerId, Fragment fragment, String tag, boolean addToBackStack,
                               int enterAnim, int exitAnim) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        if (enterAnim != 0 && exitAnim != 0) {
            ft.setCustomAnimations(enterAnim, exitAnim);
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.add(containerId, fragment, tag);
        ft.commit();
    }

    //********************************* ACTION BAR ******************************************************************************************************************

    protected void setActionbarTitle(String title) {
        BaseActivity baseActivity = getBaseActivity();

        if (baseActivity != null) {
            baseActivity.setActionbarTitle(title);
        }
    }

    protected void setActionbarTitle(int titleResourceId) {
        setActionbarTitle(getResources().getString(titleResourceId));
    }

    //********************************* BASE ACTIVITY ****************************************************************************************************************

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    //********************************* SHOW DIALOG *******************************************************************************************************************

    public void showNoInternetConnectionError() {
        InfoDialog infoDialog = InfoDialog.newInstance(
                R.string.dialog_no_internet_connection_title,
                R.string.dialog_error_description
        );

        infoDialog.setInfoDialogListener(null
        );
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getChildFragmentManager(), null);
    }

    public void showError(Throwable e) {
        InfoDialog infoDialog = InfoDialog.newInstance(
                R.string.dialog_no_internet_connection_title,
                R.string.dialog_error_description
        );
        infoDialog.setCloseButtonRes(R.string.global_close);
        infoDialog.show(getChildFragmentManager(), null);
    }

    //********************************* LOADING **********************************************************************************************************************

    public void displayLoadingDialog() {
        displayLoadingDialog("Loading");
    }

    public void displayLoadingDialog(String s) {
        hideLoadingDialog();

        mProgressDialog = new LoadingDialog(getContext());
        mProgressDialog.show();
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void displayLoadMoreDialog() {
        displayLoadMoreDialog(getResources().getString(R.string.global_loading));
    }

    public void displayLoadMoreDialog(String s) {
        hideLoadMoreDialog();

        mProgressLoadMoreDialog = new LoadMoreDialog(getContext());
        mProgressLoadMoreDialog.show();
    }

    public void hideLoadMoreDialog() {
        if (mProgressLoadMoreDialog != null) {
            mProgressLoadMoreDialog.dismiss();
        }
    }
}
