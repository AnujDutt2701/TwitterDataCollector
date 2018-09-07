package com.anujdutt.twitterdatacollector.util;

import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.viewmodel.AddTopicDialogFragmentViewModel;

public class TopicFormatter {

    public static Topic formatNewTopic(AddTopicDialogFragmentViewModel.AddNewTopicQueryParams addNewTopicQueryParams) {

        return new Topic(addNewTopicQueryParams.query, addNewTopicQueryParams.displayName);
    }
}
