package com.anujdutt.twitterdatacollector.viewmodel;

import com.anujdutt.twitterdatacollector.application.TwitterDataCollectorApplication;
import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.repository.TopicRepository;
import com.anujdutt.twitterdatacollector.util.SelectParamsViewModelListener;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel implements SelectParamsViewModelListener {

    private final MediatorLiveData<MainActivityParams> currencyDetailsActivityParamsMutableLiveData;

    private LiveData<Topic> selectedTopic;

    private MutableLiveData<Integer> selectedTopicId;

    private TopicRepository topicRepository;

    @Inject
    public MainActivityViewModel(@NonNull TwitterDataCollectorApplication application, TopicRepository topicRepository) {
        super(application);
        this.topicRepository = topicRepository;
        currencyDetailsActivityParamsMutableLiveData = new MediatorLiveData<>();
        currencyDetailsActivityParamsMutableLiveData.setValue(new MainActivityParams());
    }


    @Override
    public void setLanguage(String language) {
        MainActivityParams mainActivityParams = currencyDetailsActivityParamsMutableLiveData.getValue();
        if (mainActivityParams != null) {
            mainActivityParams.language = language;
        }
        currencyDetailsActivityParamsMutableLiveData.setValue(mainActivityParams);
    }

    @Override
    public void setTopic(Topic topic) {
        MainActivityParams mainActivityParams = currencyDetailsActivityParamsMutableLiveData.getValue();
        if (mainActivityParams != null) {
            mainActivityParams.topic = topic;
        }
        currencyDetailsActivityParamsMutableLiveData.setValue(mainActivityParams);
    }

    @Override
    public void setLocation(String location) {
        MainActivityParams mainActivityParams = currencyDetailsActivityParamsMutableLiveData.getValue();
        if (mainActivityParams != null) {
            mainActivityParams.location = location;
        }
        currencyDetailsActivityParamsMutableLiveData.setValue(mainActivityParams);
    }

    public MediatorLiveData<MainActivityParams> getParamsLiveData() {
        return currencyDetailsActivityParamsMutableLiveData;
    }

    public static class MainActivityParams {

        public Topic topic;

        public String language;

        public String location;

        public MainActivityParamsStatus getStatusOfParams() {
            if (topic != null && language != null && location != null) {
                return MainActivityParamsStatus.READY;
            } else {
                return MainActivityParamsStatus.NOT_READY;
            }
        }

        public enum MainActivityParamsStatus {

            READY,
            NOT_READY
        }
    }

} 
