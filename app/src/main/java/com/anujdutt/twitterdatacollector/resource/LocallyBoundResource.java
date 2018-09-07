package com.anujdutt.twitterdatacollector.resource;


import com.anujdutt.twitterdatacollector.util.AppExecutor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public abstract class LocallyBoundResource<ResourceType> {

    AppExecutor appExecutor;

    MediatorLiveData<Resource<ResourceType>> resultMutableLiveData = new MediatorLiveData<>();

    public LocallyBoundResource(AppExecutor appExecutor) {
        this.appExecutor = appExecutor;
        resultMutableLiveData.setValue(Resource.<ResourceType>loading(null));
        resultMutableLiveData.addSource(loadFromDb(), new Observer<ResourceType>() {

            @Override
            public void onChanged(@Nullable ResourceType resourceType) {
                resultMutableLiveData.setValue(Resource.success(resourceType));
            }
        });
    }

    public abstract LiveData<ResourceType> loadFromDb();

    public LiveData<Resource<ResourceType>> asLiveData() {
        return resultMutableLiveData;
    }
}
