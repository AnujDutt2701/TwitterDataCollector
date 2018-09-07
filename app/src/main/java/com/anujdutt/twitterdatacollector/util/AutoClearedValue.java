package com.anujdutt.twitterdatacollector.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AutoClearedValue<T> {

    private T data;

    private Fragment fragment;

    public AutoClearedValue(T data, final Fragment fragment) {
        this.data = data;
        this.fragment = fragment;
        final FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                if (f == fragment) {
                    AutoClearedValue.this.data = null;
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                }
            }
        }, false);
    }

    public T get() {
        return data;
    }
}
