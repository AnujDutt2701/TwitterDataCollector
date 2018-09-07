package com.anujdutt.twitterdatacollector.di.module;

import com.anujdutt.twitterdatacollector.di.scope.TwitterDataCollectorScope;
import com.anujdutt.twitterdatacollector.rest.ApiResponseCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class NetworkModule {

    @TwitterDataCollectorScope
    @Provides
    ApiResponseCallAdapterFactory apiResponseCallAdapterFactory() {
        return new ApiResponseCallAdapterFactory();
    }

    @TwitterDataCollectorScope
    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    @TwitterDataCollectorScope
    @Provides
    OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}
