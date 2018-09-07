package com.anujdutt.twitterdatacollector.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit mRetrofit;

    public static String TWITTER_API_BASE_URl = "https://api.twitter.com/1.1/";

    public static String TWITTER_API_BEARER_TOKEN = "https://api.twitter.com/1.1/";

    public static String authorizationHeader = "OAuth oauth_consumer_key=\"brBvPa1dSPuWdurtPyUD2lGNf\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1536089967\",oauth_nonce=\"xx6prDT2bUw\",oauth_version=\"1.0\",oauth_signature=\"LnF%2Fn%2Fv7TnxetqiQ%2B9AFPxChbm8%3D\"";

    public static String oauthConsumerKey = "brBvPa1dSPuWdurtPyUD2lGNf";

    public static Retrofit getClient(String baseUrl) {
        if(mRetrofit!= null && mRetrofit.baseUrl().url().toString().equals(baseUrl)) {
            return mRetrofit;
        } else {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(baseUrl);
            builder.addConverterFactory(GsonConverterFactory.create());
            mRetrofit = builder.build();
            return mRetrofit;
        }
    }
}
