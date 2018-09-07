package com.anujdutt.twitterdatacollector.repository;

import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.resource.ApiResponse;
import com.anujdutt.twitterdatacollector.resource.NetworkBoundResource;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.anujdutt.twitterdatacollector.rest.ApiService;
import com.anujdutt.twitterdatacollector.util.AppExecutor;
import com.anujdutt.twitterdatacollector.viewmodel.SearchTweetFragmentViewModel;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.params.Geocode;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class SearchRepository {

    public AppExecutor appExecutor;

    public ApiService apiService;

    public SearchRepository(AppExecutor appExecutor, ApiService apiService) {
        this.appExecutor = appExecutor;
        this.apiService = apiService;
    }

    public LiveData<Resource<List<Tweet>>> fetchTweets(final SearchTweetFragmentViewModel.SearchTweetFragmentViewModelParams searchTweetFragmentViewModelParams) {
        return new NetworkBoundResource<List<Tweet>, Search>(appExecutor) {

            @Override
            public LiveData<ApiResponse<Search>> createCall() {

                return apiService.getSearchData("Bearer AAAAAAAAAAAAAAAAAAAAAB6a8QAAAAAAJ0yCvbojuzrpg8wfIdOWS3YAIUE%3DYrdG1L2b2TaCKOh8yanOwEkdU6vpbVseXiiJ28orbVfZ3K3isp", searchTweetFragmentViewModelParams.query);
            }

            @Override
            public void processNetworkCallResponse(final ApiResponse<Search> searchApiResponse) {
                appExecutor.workerThread.execute(new Runnable() {

                    @Override
                    public void run() {
                        if (searchApiResponse.isSuccessful() && searchApiResponse.body != null) {
                            postValue(Resource.success(searchApiResponse.body.tweets));
                        } else {
                            postValue(Resource.error(searchApiResponse.errorMessage, new ArrayList<Tweet>().subList(0,0)));
                        }
                    }
                });
            }

            @Override
            public void setInitialData() {
                appExecutor.workerThread.execute(new Runnable() {

                    @Override
                    public void run() {
                        postValue(Resource.loading(new ArrayList<Tweet>().subList(0,0)));
                    }
                });
            }

            @Override
            public boolean shouldFetchDataFromNetwork() {
                return true;
            }
        }.asLiveData();
    }
}
