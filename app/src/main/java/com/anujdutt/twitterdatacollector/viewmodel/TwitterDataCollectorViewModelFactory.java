package com.anujdutt.twitterdatacollector.viewmodel;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TwitterDataCollectorViewModelFactory implements ViewModelProvider.Factory {


    Map<Class<? extends ViewModel>, Provider<ViewModel>> classProviderMap;

    @Inject
    public TwitterDataCollectorViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> classProviderMap) {
        this.classProviderMap = classProviderMap;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = classProviderMap.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : classProviderMap.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
