package com.anujdutt.twitterdatacollector.resource;

import com.anujdutt.twitterdatacollector.util.AppExecutor;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    MediatorLiveData<Resource<ResultType>> mediatorLiveData = new MediatorLiveData<>();

    private AppExecutor appExecutor;

    public NetworkBoundResource(AppExecutor appExecutor) {
        this.appExecutor = appExecutor;
        setInitialData();
        if (shouldFetchDataFromNetwork()) {
            fetchFromNetwork();
        }
    }

    private void fetchFromNetwork() {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        mediatorLiveData.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {

            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> requestTypeApiResponse) {
                processNetworkCallResponse(requestTypeApiResponse);
            }
        });
    }

    public abstract LiveData<ApiResponse<RequestType>> createCall();

    public abstract void processNetworkCallResponse(ApiResponse<RequestType> requestTypeApiResponse);

    public abstract void setInitialData();

    public abstract boolean shouldFetchDataFromNetwork();

    public LiveData<Resource<ResultType>> asLiveData() {
        return mediatorLiveData;
    }

    @WorkerThread
    protected void postValue(Resource<ResultType> newValue) {
        if (! Objects.equals(mediatorLiveData.getValue(), newValue)) {
            mediatorLiveData.postValue(newValue);
        }
    }
}