package com.anujdutt.twitterdatacollector.repository;

import com.anujdutt.twitterdatacollector.dao.TopicDao;
import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.resource.LocallyBoundResource;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.anujdutt.twitterdatacollector.util.AppExecutor;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TopicRepository {

    public AppExecutor appExecutor;
    public TopicDao topicDao;

    public TopicRepository(AppExecutor appExecutor, TopicDao topicDao) {
        this.appExecutor = appExecutor;
        this.topicDao = topicDao;
    }

    public LiveData<Resource<List<Topic>>> fetchAllTopics() {
        return new LocallyBoundResource<List<Topic>>(appExecutor) {

            @Override
            public LiveData<List<Topic>> loadFromDb() {
                return topicDao.fetchAllTopics();
            }
        }.asLiveData();
    }

    public void saveNewTopic(final Topic topic) {
        appExecutor.diskIO.execute(new Runnable() {

            @Override
            public void run() {
                topicDao.insertTopic(topic);
            }
        });
    }

}
