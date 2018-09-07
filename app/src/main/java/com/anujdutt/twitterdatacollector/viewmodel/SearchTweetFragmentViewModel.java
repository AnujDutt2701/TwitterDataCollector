package com.anujdutt.twitterdatacollector.viewmodel;


import com.anujdutt.twitterdatacollector.repository.SearchRepository;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class SearchTweetFragmentViewModel extends ViewModel {

    private LiveData<Resource<List<Tweet>>> results;

    private MutableLiveData<SearchTweetFragmentViewModelParams> searchTweetFragmentViewModelParamsMutableLiveData;

    private MutableLiveData<Integer> reloadCount = new MutableLiveData<>();

    @Inject
    public SearchTweetFragmentViewModel(final SearchRepository repository) {
        searchTweetFragmentViewModelParamsMutableLiveData = new MutableLiveData<>();
        searchTweetFragmentViewModelParamsMutableLiveData.setValue(new SearchTweetFragmentViewModelParams());
        results = Transformations.switchMap(searchTweetFragmentViewModelParamsMutableLiveData, new Function<SearchTweetFragmentViewModelParams, LiveData<Resource<List<Tweet>>>>() {

            @Override
            public LiveData<Resource<List<Tweet>>> apply(SearchTweetFragmentViewModelParams input) {
                return repository.fetchTweets(input);
            }
        });
        reloadCount.setValue(0);
    }

    public LiveData<Resource<List<Tweet>>> getResults() {
        return results;
    }

    public MutableLiveData<SearchTweetFragmentViewModelParams> getQueryParams() {
        return searchTweetFragmentViewModelParamsMutableLiveData;
    }

    public void incrementReloadCount() {
        reloadCount.setValue((reloadCount.getValue() != null ? reloadCount.getValue() : 0) + 1);
    }

    public static class SearchTweetFragmentViewModelParams {

        public String query;
        //public Geocode geocode;
        public String language;

        public enum QueryParamsStatus {
            READY,
            NOT_READY
        }

        public QueryParamsStatus getQueryParamsStatus() {
            if (query != null && language != null) {
                return QueryParamsStatus.READY;
            } else {
                return QueryParamsStatus.NOT_READY;
            }
        }
    }
}
