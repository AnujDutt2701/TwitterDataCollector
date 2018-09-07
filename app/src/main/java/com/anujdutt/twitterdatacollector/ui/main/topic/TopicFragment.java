package com.anujdutt.twitterdatacollector.ui.main.topic;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.adapter.TopicsAdapter;
import com.anujdutt.twitterdatacollector.binding.FragmentBindingComponent;
import com.anujdutt.twitterdatacollector.databinding.FragmentTopicsBinding;
import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.anujdutt.twitterdatacollector.ui.main.addtopic.AddTopicDialogFragment;
import com.anujdutt.twitterdatacollector.util.AutoClearedValue;
import com.anujdutt.twitterdatacollector.viewmodel.TopicFragmentViewModel;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.support.AndroidSupportInjection;

public class TopicFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    TopicFragmentViewModel topicFragmentViewModel;

    FragmentTopicsBinding topicsBinding;

    AutoClearedValue<TopicsAdapter> topicsAdapterAutoClearedValue;

    FragmentBindingComponent fragmentBindingComponent;

    public TopicFragment() {
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
        topicsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_topics, container, false, fragmentBindingComponent);
        setupToolbar();
        setupRecyclerView();
        setupClickListeners();
        return topicsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        topicFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(TopicFragmentViewModel.class);
        topicFragmentViewModel.getResults().observe(this, new Observer<Resource<List<Topic>>>() {

            @Override
            public void onChanged(@Nullable Resource<List<Topic>> listResource) {
                if (listResource != null && topicsAdapterAutoClearedValue.get() != null) {
                    topicsAdapterAutoClearedValue.get().setDataList(listResource.data);
                    if (listResource.status == Resource.Status.LOADING) {
                        topicsBinding.srlTopicsList.setRefreshing(true);
                        showContentLayout();
                    } else if (listResource.status == Resource.Status.SUCCESS) {
                        topicsBinding.srlTopicsList.setRefreshing(false);
                        if (listResource.data == null || listResource.data.size() == 0) {
                            showErrorLayout();
                        } else {
                            showContentLayout();
                        }
                    } else {
                        topicsBinding.srlTopicsList.setRefreshing(false);
                        showErrorLayout();
                    }
                } else {
                    topicsBinding.srlTopicsList.setRefreshing(false);
                    topicsAdapterAutoClearedValue.get().setDataList(new ArrayList<Topic>());
                    showErrorLayout();
                }
            }
        });
        topicsBinding.srlTopicsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                topicFragmentViewModel.incrementReloadCount();
            }
        });
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(topicsBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.topics);
    }

    private void setupRecyclerView() {
        topicsAdapterAutoClearedValue = new AutoClearedValue<>(new TopicsAdapter(fragmentBindingComponent), this);
        topicsBinding.rvTopicsResult.setHasFixedSize(true);
        topicsBinding.rvTopicsResult.setAdapter(topicsAdapterAutoClearedValue.get());
        topicsBinding.rvTopicsResult.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildLayoutPosition(view);
                outRect.left = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
                outRect.right = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
                outRect.bottom = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
                if(position==0){
                    outRect.top = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
                }
            }
        });
    }

    private void setupClickListeners() {
        topicsBinding.fabAddNewTopic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddTopicDialogFragment addTopicDialogFragment = AddTopicDialogFragment.getInstance();
                addTopicDialogFragment.show(getChildFragmentManager(), "AddTopicDialogFragment");
            }
        });
    }

    private void showErrorLayout() {
        topicsBinding.tvError.setVisibility(View.VISIBLE);
        topicsBinding.rvTopicsResult.setVisibility(View.GONE);
    }

    private void showContentLayout() {
        topicsBinding.tvError.setVisibility(View.GONE);
        topicsBinding.rvTopicsResult.setVisibility(View.VISIBLE);
    }
}
