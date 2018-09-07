package com.anujdutt.twitterdatacollector.binding;

import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;

public class FragmentBindingComponent implements DataBindingComponent {

    private Fragment fragment;

    public FragmentBindingComponent(Fragment fragment) {
        this.fragment = fragment;
    }

    public FragmentBindingComponent() {
    }
}