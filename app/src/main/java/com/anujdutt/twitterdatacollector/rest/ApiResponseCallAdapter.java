package com.anujdutt.twitterdatacollector.rest;


import com.anujdutt.twitterdatacollector.resource.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiResponseCallAdapter<T> implements CallAdapter<T, LiveData<ApiResponse<T>>> {

    private final Type responseType;

    public ApiResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(@NonNull final Call<T> call) {
        return new LiveData<ApiResponse<T>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();

                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(@NonNull Call<T> call,@NonNull Response<T> response) {
                            postValue(new ApiResponse<T>(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
                            postValue(new ApiResponse<T>(throwable));
                        }
                    });
                }
            }
        };
    }
}