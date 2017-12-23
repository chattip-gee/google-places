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

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected T binding;

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
}
