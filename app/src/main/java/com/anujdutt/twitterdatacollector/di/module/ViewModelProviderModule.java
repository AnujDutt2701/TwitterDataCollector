package com.anujdutt.twitterdatacollector.di.module;


import com.anujdutt.twitterdatacollector.di.key.ViewModelKey;
import com.anujdutt.twitterdatacollector.viewmodel.AddTopicDialogFragmentViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.MainActivityViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.SelectTopicDialogFragmentViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.TopicFragmentViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.SearchTweetFragmentViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.TwitterDataCollectorViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelProviderModule {

    @IntoMap
    @Binds
    @ViewModelKey(TopicFragmentViewModel.class)
    abstract ViewModel contributesAddTopicFragmentViewModel(TopicFragmentViewModel addTopicFragment);

    @IntoMap
    @Binds
    @ViewModelKey(SearchTweetFragmentViewModel.class)
    abstract ViewModel contributesSearchTweetFragmentViewModel(SearchTweetFragmentViewModel searchTweetFragmentViewModel);

    @IntoMap
    @Binds
    @ViewModelKey(SelectTopicDialogFragmentViewModel.class)
    abstract ViewModel contributesSelectTopicDialogFragmentViewModel(SelectTopicDialogFragmentViewModel selectTopicDialogFragmentViewModel);

    @IntoMap
    @Binds
    @ViewModelKey(AddTopicDialogFragmentViewModel.class)
    abstract ViewModel contributesAddTopicDialogFragmentViewModel(AddTopicDialogFragmentViewModel addTopicDialogFragmentViewModel);

    @IntoMap
    @Binds
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel contributesMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(TwitterDataCollectorViewModelFactory factory);
}
