package com.anujdutt.twitterdatacollector.ui.main.searchtweet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.adapter.SearchResultAdapter;
import com.anujdutt.twitterdatacollector.adapter.TopicsAdapter;
import com.anujdutt.twitterdatacollector.binding.FragmentBindingComponent;
import com.anujdutt.twitterdatacollector.databinding.FragmentSearchTweetsBinding;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.anujdutt.twitterdatacollector.ui.main.addtopic.AddTopicDialogFragment;
import com.anujdutt.twitterdatacollector.ui.main.selecttopic.SelectTopicDialogFragment;
import com.anujdutt.twitterdatacollector.util.AutoClearedValue;
import com.anujdutt.twitterdatacollector.viewmodel.SearchTweetFragmentViewModel;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.support.AndroidSupportInjection;

public class SearchTweetFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SearchTweetFragmentViewModel searchTweetFragmentViewModel;

    FragmentSearchTweetsBinding fragmentSearchTweetsBinding;

    AutoClearedValue<SearchResultAdapter> searchResultAdapterAutoClearedValue;

    FragmentBindingComponent fragmentBindingComponent;

    public SearchTweetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentBindingComponent = new FragmentBindingComponent(this);
        fragmentSearchTweetsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_tweets, container, false, fragmentBindingComponent);
        setupToolbar();
        setupRecyclerView();
        setupClickListeners();
        return fragmentSearchTweetsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchTweetFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchTweetFragmentViewModel.class);
        searchTweetFragmentViewModel.getResults().observe(this, new Observer<Resource<List<Tweet>>>() {

            @Override
            public void onChanged(@Nullable Resource<List<Tweet>> listResource) {
                if (listResource != null && searchResultAdapterAutoClearedValue.get() != null) {
                    searchResultAdapterAutoClearedValue.get().setDataList(listResource.data);
                    if (listResource.status == Resource.Status.LOADING) {
                        fragmentSearchTweetsBinding.srlTweetList.setRefreshing(true);
                        showContentLayout();
                    } else if (listResource.status == Resource.Status.SUCCESS) {
                        fragmentSearchTweetsBinding.srlTweetList.setRefreshing(false);
                        if (listResource.data == null || listResource.data.size() == 0) {
                            showErrorLayout();
                        } else {
                            showContentLayout();
                        }
                    } else {
                        fragmentSearchTweetsBinding.srlTweetList.setRefreshing(false);
                        showErrorLayout();
                    }
                } else {
                    fragmentSearchTweetsBinding.srlTweetList.setRefreshing(false);
                    searchResultAdapterAutoClearedValue.get().setDataList(new ArrayList<Tweet>());
                    showErrorLayout();
                }
            }
        });
        searchTweetFragmentViewModel.getQueryParams().observe(this, new Observer<SearchTweetFragmentViewModel.SearchTweetFragmentViewModelParams>() {
            @Override
            public void onChanged(SearchTweetFragmentViewModel.SearchTweetFragmentViewModelParams searchTweetFragmentViewModelParams) {
                fragmentSearchTweetsBinding.setQueryParams(searchTweetFragmentViewModelParams);
            }
        });
        fragmentSearchTweetsBinding.srlTweetList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                searchTweetFragmentViewModel.incrementReloadCount();
            }
        });
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentSearchTweetsBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.search);
    }

    private void setupRecyclerView() {
        searchResultAdapterAutoClearedValue = new AutoClearedValue<>(new SearchResultAdapter(fragmentBindingComponent), this);
        fragmentSearchTweetsBinding.rvTwitterResult.setHasFixedSize(true);
        fragmentSearchTweetsBinding.rvTwitterResult.setAdapter(searchResultAdapterAutoClearedValue.get());
    }

    private void setupClickListeners() {
        fragmentSearchTweetsBinding.apbTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTopicDialogFragment selectTopicDialogFragment = SelectTopicDialogFragment.getInstance();
                selectTopicDialogFragment.show(getChildFragmentManager(), "SelectTopicDialogFragment");
            }
        });
    }

    private void showErrorLayout() {
        fragmentSearchTweetsBinding.tvError.setVisibility(View.VISIBLE);
        fragmentSearchTweetsBinding.rvTwitterResult.setVisibility(View.GONE);
    }

    private void showContentLayout() {
        fragmentSearchTweetsBinding.tvError.setVisibility(View.GONE);
        fragmentSearchTweetsBinding.rvTwitterResult.setVisibility(View.VISIBLE);
    }
}