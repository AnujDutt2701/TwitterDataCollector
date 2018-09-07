package com.anujdutt.twitterdatacollector.dao;


import com.anujdutt.twitterdatacollector.entity.Topic;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface TopicDao {

    @Query("SELECT * FROM topic ORDER BY id")
    LiveData<List<Topic>> fetchAllTopics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTopicsList(List<Topic> topics);

    @Insert
    Long insertTopic(Topic topic);


}
