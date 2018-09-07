package com.anujdutt.twitterdatacollector.viewmodel;

import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.repository.TopicRepository;
import com.anujdutt.twitterdatacollector.resource.Resource;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class SelectTopicDialogFragmentViewModel extends ViewModel {

    private MutableLiveData<Integer> reloadLiveData;
    private LiveData<Resource<List<Topic>>> topicListLiveData;

    @Inject
    public SelectTopicDialogFragmentViewModel(TopicRepository topicRepository) {
        reloadLiveData = new MutableLiveData<>();
        setOptionsDependencies(topicRepository);
    }

    private void setOptionsDependencies(final TopicRepository topicRepository) {
        topicListLiveData = Transformations.switchMap(reloadLiveData, new Function<Integer, LiveData<Resource<List<Topic>>>>() {

            @Override
            public LiveData<Resource<List<Topic>>> apply(Integer input) {
                return topicRepository.fetchAllTopics();
            }
        });
        reloadLiveData.setValue(1);
    }

    public void reloadData() {
        reloadLiveData.setValue(reloadLiveData.getValue() + 1);
    }

    public LiveData<Resource<List<Topic>>> getTopicListLiveData() {
        return topicListLiveData;
    }
}