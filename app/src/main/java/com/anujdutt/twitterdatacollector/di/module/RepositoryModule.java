package com.anujdutt.twitterdatacollector.di.module;


import com.anujdutt.twitterdatacollector.dao.TopicDao;
import com.anujdutt.twitterdatacollector.repository.SearchRepository;
import com.anujdutt.twitterdatacollector.repository.TopicRepository;
import com.anujdutt.twitterdatacollector.rest.ApiService;
import com.anujdutt.twitterdatacollector.util.AppExecutor;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public TopicRepository topicRepository(AppExecutor appExecutor, TopicDao topicDao) {
        return new TopicRepository(appExecutor, topicDao);
    }

    @Provides
    public SearchRepository searchRepository(AppExecutor appExecutor, ApiService apiService) {
        return new SearchRepository(appExecutor, apiService);
    }
}
