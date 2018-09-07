package com.anujdutt.twitterdatacollector.util;

import com.anujdutt.twitterdatacollector.entity.Topic;

public interface SelectParamsViewModelListener {

    void setLanguage(String language);

    void setTopic(Topic topic);

    void setLocation(String location);
}
