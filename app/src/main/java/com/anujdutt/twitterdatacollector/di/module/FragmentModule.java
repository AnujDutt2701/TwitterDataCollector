package com.anujdutt.twitterdatacollector.di.module;


import com.anujdutt.twitterdatacollector.ui.main.addtopic.AddTopicDialogFragment;
import com.anujdutt.twitterdatacollector.ui.main.searchtweet.SearchTweetFragment;
import com.anujdutt.twitterdatacollector.ui.main.selecttopic.SelectTopicDialogFragment;
import com.anujdutt.twitterdatacollector.ui.main.topic.TopicFragment;

import androidx.fragment.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.DaggerDialogFragment;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {FragmentModule.TopicFragmentSubComponent.class, FragmentModule.SearchTweetFragmentSubComponent.class})
public abstract class FragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(TopicFragment.class)
    public abstract AndroidInjector.Factory<? extends Fragment> contributesTopicFragment(TopicFragmentSubComponent.Builder builder);

    @Subcomponent
    public interface TopicFragmentSubComponent extends AndroidInjector<TopicFragment> {

        @Subcomponent.Builder
        public abstract class Builder extends AndroidInjector.Builder<TopicFragment> {

        }
    }

    @Binds
    @IntoMap
    @FragmentKey(SearchTweetFragment.class)
    public abstract AndroidInjector.Factory<? extends Fragment> contributesSearchTweetFragment(SearchTweetFragmentSubComponent.Builder builder);

    @Subcomponent
    public interface SearchTweetFragmentSubComponent extends AndroidInjector<SearchTweetFragment> {

        @Subcomponent.Builder
        public abstract class Builder extends AndroidInjector.Builder<SearchTweetFragment> {

        }
    }

    @ContributesAndroidInjector
    public abstract SelectTopicDialogFragment selectTopicDialogFragment();

    @ContributesAndroidInjector
    public abstract AddTopicDialogFragment addTopicDialogFragment();

//    @Binds
//    @IntoMap
//    @FragmentKey(AddTopicDialogFragment.class)
//    public abstract AndroidInjector.Factory<? extends DaggerDialogFragment> contributesAddTopicDialogFragment(AddTopicDialogFragmentSubComponent.Builder builder);
//
//    @Subcomponent
//    public interface AddTopicDialogFragmentSubComponent extends AndroidInjector<AddTopicDialogFragment> {
//
//        @Subcomponent.Builder
//        public abstract class Builder extends AndroidInjector.Builder<AddTopicDialogFragment> {
//
//        }
//    }


}
