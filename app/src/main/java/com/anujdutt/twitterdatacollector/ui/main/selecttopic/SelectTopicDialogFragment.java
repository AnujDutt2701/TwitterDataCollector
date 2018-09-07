package com.anujdutt.twitterdatacollector.ui.main.selecttopic;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.adapter.TopicListSelectionAdapter;
import com.anujdutt.twitterdatacollector.binding.FragmentBindingComponent;
import com.anujdutt.twitterdatacollector.databinding.DialogFragmentSelectTopicBinding;
import com.anujdutt.twitterdatacollector.entity.Topic;
import com.anujdutt.twitterdatacollector.resource.Resource;
import com.anujdutt.twitterdatacollector.ui.main.MainActivity;
import com.anujdutt.twitterdatacollector.util.AutoClearedValue;
import com.anujdutt.twitterdatacollector.util.SelectParamsViewModelListener;
import com.anujdutt.twitterdatacollector.viewmodel.MainActivityViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.SelectTopicDialogFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.DaggerDialogFragment;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerAppCompatDialogFragment;

public class SelectTopicDialogFragment extends DaggerAppCompatDialogFragment {

    @Inject
    ViewModelProvider.Factory applicationViewModelFactory;

    SelectTopicDialogFragmentViewModel selectTopicDialogFragmentViewModel;

    SelectParamsViewModelListener parentViewModel;

    FragmentBindingComponent fragmentBindingComponent;

    DialogFragmentSelectTopicBinding dialogFragmentSelectTopicBinding;

    AutoClearedValue<TopicListSelectionAdapter> topicListSelectionAdapterAutoClearedValue;

    public SelectTopicDialogFragment() {
        // Required empty public constructor
    }

    public static SelectTopicDialogFragment getInstance() {
        return new SelectTopicDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBindingComponent = new FragmentBindingComponent();
        dialogFragmentSelectTopicBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_select_topic, container, false, fragmentBindingComponent);
        setupRecyclerViews();
        return dialogFragmentSelectTopicBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectTopicDialogFragmentViewModel = ViewModelProviders.of(this, applicationViewModelFactory).get(SelectTopicDialogFragmentViewModel.class);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            parentViewModel = ViewModelProviders.of((MainActivity) getActivity(), applicationViewModelFactory).get(MainActivityViewModel.class);
        }
        setupLiveDataListener();
        setupClickListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void setupClickListeners() {
//        dialogFragmentSelectTopicBinding.tvSave.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
    }


    private void setupLiveDataListener() {
        selectTopicDialogFragmentViewModel.getTopicListLiveData().observe(this, new Observer<Resource<List<Topic>>>() {

            @Override
            public void onChanged(@Nullable Resource<List<Topic>> listResource) {
                if (listResource != null && topicListSelectionAdapterAutoClearedValue.get() != null) {
                    if (listResource.status == Resource.Status.LOADING) {
                        dialogFragmentSelectTopicBinding.srlSelectTopic.setRefreshing(true);
                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
                        showContentLayout();
                    } else if (listResource.status == Resource.Status.SUCCESS) {
                        dialogFragmentSelectTopicBinding.srlSelectTopic.setRefreshing(false);
                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
                        showContentLayout();
                    } else {
                        dialogFragmentSelectTopicBinding.srlSelectTopic.setRefreshing(false);
                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
                        showErrorLayout();
                    }
                } else {
                    dialogFragmentSelectTopicBinding.srlSelectTopic.setRefreshing(false);
                    topicListSelectionAdapterAutoClearedValue.get().setDataList(new ArrayList<Topic>());
                    showErrorLayout();
                }
            }
        });
    }


    private void setupRecyclerViews() {
        topicListSelectionAdapterAutoClearedValue = new AutoClearedValue<>(new TopicListSelectionAdapter(fragmentBindingComponent, new TopicListSelectionAdapter.TopicClickCallback() {

            @Override
            public void onItemClick(Topic topic) {
                parentViewModel.setTopic(topic);
                getDialog().dismiss();
            }
        }), this);
        dialogFragmentSelectTopicBinding.rvTopicList.setHasFixedSize(true);
        dialogFragmentSelectTopicBinding.rvTopicList.setAdapter(topicListSelectionAdapterAutoClearedValue.get());
        dialogFragmentSelectTopicBinding.srlSelectTopic.setEnabled(false);
    }

    private void showErrorLayout() {
        dialogFragmentSelectTopicBinding.llTopicPlaceholder.setVisibility(View.VISIBLE);
        dialogFragmentSelectTopicBinding.rvTopicList.setVisibility(View.GONE);
    }

    private void showContentLayout() {
        dialogFragmentSelectTopicBinding.llTopicPlaceholder.setVisibility(View.GONE);
        dialogFragmentSelectTopicBinding.rvTopicList.setVisibility(View.VISIBLE);
    }
}