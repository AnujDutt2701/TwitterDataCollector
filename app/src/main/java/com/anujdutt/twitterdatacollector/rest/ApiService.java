package com.anujdutt.twitterdatacollector.rest;

import com.anujdutt.twitterdatacollector.model.SearchQueryResultModel;
import com.anujdutt.twitterdatacollector.resource.ApiResponse;
import com.twitter.sdk.android.core.models.Search;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/tweets.json")
    LiveData<ApiResponse<Search>> getSearchData(@Header("Authorization") String token, @Query("q") String q);

}
