package com.anujdutt.twitterdatacollector.di.key;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

@MapKey
public @interface ViewModelKey {

    Class<? extends ViewModel> value();
}
