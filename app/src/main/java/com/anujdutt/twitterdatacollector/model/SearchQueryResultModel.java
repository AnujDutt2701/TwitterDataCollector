package com.anujdutt.twitterdatacollector.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.ModelUtils;
import com.twitter.sdk.android.core.models.SearchMetadata;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * @author anujdutt
 *
 * Maps the result of the twitter search query result.
 */
public class SearchQueryResultModel {

    @SerializedName("statuses")
    public final List<Tweet> tweets;

    @SerializedName("search_metadata")
    public final SearchMetadata searchMetadata;

    private SearchQueryResultModel() {
        this(null, null);
    }

    public SearchQueryResultModel(List<Tweet> tweets, SearchMetadata searchMetadata) {
        this.tweets = ModelUtils.getSafeList(tweets);
        this.searchMetadata = searchMetadata;
    }
}