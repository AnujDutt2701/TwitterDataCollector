package com.anujdutt.twitterdatacollector.di.module;


import com.anujdutt.twitterdatacollector.dao.TopicDao;
import com.anujdutt.twitterdatacollector.db.TwitterDataCollectorDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

    @Provides
    public TopicDao topicDao(TwitterDataCollectorDatabase twitterDataCollectorDatabase) {
        return twitterDataCollectorDatabase.topicDao();
    }
}
