package com.anujdutt.twitterdatacollector.db;


import com.anujdutt.twitterdatacollector.dao.TopicDao;
import com.anujdutt.twitterdatacollector.entity.Topic;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Topic.class}, version = 1, exportSchema = false)
public abstract class TwitterDataCollectorDatabase extends RoomDatabase {

    public abstract TopicDao topicDao();
}
