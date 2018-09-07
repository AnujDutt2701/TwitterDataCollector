package com.anujdutt.twitterdatacollector.viewmodel;

import com.anujdutt.twitterdatacollector.repository.TopicRepository;
import com.anujdutt.twitterdatacollector.util.TopicFormatter;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddTopicDialogFragmentViewModel extends ViewModel {

    private MutableLiveData<AddNewTopicQueryParams> newTopicQueryParamsMutableLiveData;

    private TopicRepository topicRepository;

    @Inject
    public AddTopicDialogFragmentViewModel(final TopicRepository repository) {
        topicRepository = repository;
        newTopicQueryParamsMutableLiveData = new MutableLiveData<>();
        newTopicQueryParamsMutableLiveData.setValue(new AddNewTopicQueryParams());
    }

    public void setTopicQuery(String query) {
        AddNewTopicQueryParams topicQueryParams = newTopicQueryParamsMutableLiveData.getValue();
        if (topicQueryParams != null) {
            topicQueryParams.query = query;
            newTopicQueryParamsMutableLiveData.setValue(topicQueryParams);
        }
    }

    public void setTopicDisplayName(String displayName) {
        AddNewTopicQueryParams topicQueryParams = newTopicQueryParamsMutableLiveData.getValue();
        if (topicQueryParams != null) {
            topicQueryParams.displayName = displayName;
            newTopicQueryParamsMutableLiveData.setValue(topicQueryParams);
        }
    }

    public void saveTopic() {
        if (newTopicQueryParamsMutableLiveData.getValue() != null &&
                newTopicQueryParamsMutableLiveData.getValue().getTopicParamsStatus() == AddNewTopicQueryParams.AddNewTopicParamsStatus.READY) {
            topicRepository.saveNewTopic(TopicFormatter.formatNewTopic(newTopicQueryParamsMutableLiveData.getValue()));
        }
    }

    public static class AddNewTopicQueryParams {

        public String query;
        public String displayName;

        public enum AddNewTopicParamsStatus {
            READY,
            NOT_READY
        }

        public AddNewTopicParamsStatus getTopicParamsStatus() {
            if (query != null && displayName != null) {
                return AddNewTopicParamsStatus.READY;
            } else {
                return AddNewTopicParamsStatus.NOT_READY;
            }
        }
    }

}
