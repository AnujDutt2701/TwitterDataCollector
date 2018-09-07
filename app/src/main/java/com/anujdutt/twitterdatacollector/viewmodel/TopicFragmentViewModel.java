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

public class TopicFragmentViewModel extends ViewModel {

    private LiveData<Resource<List<Topic>>> results;

    private MutableLiveData<Integer> reloadCount = new MutableLiveData<>();

    @Inject
    public TopicFragmentViewModel(final TopicRepository repository) {
        results = Transformations.switchMap(reloadCount, new Function<Integer, LiveData<Resource<List<Topic>>>>() {

            @Override
            public LiveData<Resource<List<Topic>>> apply(Integer input) {
                return repository.fetchAllTopics();
            }
        });
        reloadCount.setValue(0);
    }

    public LiveData<Resource<List<Topic>>> getResults() {
        return results;
    }

    public void incrementReloadCount() {
        reloadCount.setValue((reloadCount.getValue() != null ? reloadCount.getValue() : 0) + 1);
    }
}
