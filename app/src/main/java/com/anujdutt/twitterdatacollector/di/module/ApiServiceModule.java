package com.anujdutt.twitterdatacollector.di.module;

import com.anujdutt.twitterdatacollector.di.scope.TwitterDataCollectorScope;
import com.anujdutt.twitterdatacollector.rest.ApiClient;
import com.anujdutt.twitterdatacollector.rest.ApiResponseCallAdapterFactory;
import com.anujdutt.twitterdatacollector.rest.ApiService;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiServiceModule {

    @TwitterDataCollectorScope
    @Provides
    public Retrofit cryptoCompareMinApiRetrofit(@NonNull ApiResponseCallAdapterFactory apiResponseCallAdapterFactory, @NonNull OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(ApiClient.TWITTER_API_BASE_URl)
                .addCallAdapterFactory(apiResponseCallAdapterFactory)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @TwitterDataCollectorScope
    @Provides
    public ApiService cryptoCompareMinApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
