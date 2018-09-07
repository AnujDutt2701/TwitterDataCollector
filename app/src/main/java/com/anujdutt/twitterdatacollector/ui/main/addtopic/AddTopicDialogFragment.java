package com.anujdutt.twitterdatacollector.ui.main.addtopic;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.binding.FragmentBindingComponent;
import com.anujdutt.twitterdatacollector.databinding.DialogFragmentAddTopicBinding;
import com.anujdutt.twitterdatacollector.viewmodel.AddTopicDialogFragmentViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatDialogFragment;

public class AddTopicDialogFragment extends DaggerAppCompatDialogFragment {

    @Inject
    ViewModelProvider.Factory applicationViewModelFactory;

    AddTopicDialogFragmentViewModel addTopicDialogFragmentViewModel;

    FragmentBindingComponent fragmentBindingComponent;

    DialogFragmentAddTopicBinding dialogFragmentAddTopicBinding;

    public AddTopicDialogFragment() {
        // Required empty public constructor
    }

    public static AddTopicDialogFragment getInstance() {
        return new AddTopicDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBindingComponent = new FragmentBindingComponent();
        dialogFragmentAddTopicBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_add_topic, container, false, fragmentBindingComponent);
        return dialogFragmentAddTopicBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTopicDialogFragmentViewModel = ViewModelProviders.of(this, applicationViewModelFactory).get(AddTopicDialogFragmentViewModel.class);
        setupClickListeners();
        initialiseTextWatcher();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private void setupClickListeners() {
        dialogFragmentAddTopicBinding.apbAddTopic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTopicDialogFragmentViewModel.saveTopic();
                getDialog().dismiss();
            }
        });
    }

    private void initialiseTextWatcher() {
        dialogFragmentAddTopicBinding.etTopicQuery.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addTopicDialogFragmentViewModel.setTopicQuery(s.toString());
            }
        });

        dialogFragmentAddTopicBinding.etTopicDisplayName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addTopicDialogFragmentViewModel.setTopicDisplayName(s.toString());
            }
        });
    }


    private void setupLiveDataListener() {
//        addTopicDialogFragmentViewModel.getTopicListLiveData().observe(this, new Observer<Resource<List<Topic>>>() {
//
//            @Override
//            public void onChanged(@Nullable Resource<List<Topic>> listResource) {
//                if (listResource != null && topicListSelectionAdapterAutoClearedValue.get() != null) {
//                    if (listResource.status == Resource.Status.LOADING) {
//                        dialogFragmentAddTopicBinding.srlSelectTopic.setRefreshing(true);
//                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
//                        showContentLayout();
//                    } else if (listResource.status == Resource.Status.SUCCESS) {
//                        dialogFragmentAddTopicBinding.srlSelectTopic.setRefreshing(false);
//                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
//                        showContentLayout();
//                    } else {
//                        dialogFragmentAddTopicBinding.srlSelectTopic.setRefreshing(false);
//                        topicListSelectionAdapterAutoClearedValue.get().setDataList(listResource.data);
//                        showErrorLayout();
//                    }
//                } else {
//                    dialogFragmentAddTopicBinding.srlSelectTopic.setRefreshing(false);
//                    topicListSelectionAdapterAutoClearedValue.get().setDataList(new ArrayList<Topic>());
//                    showErrorLayout();
//                }
//            }
//        });
    }

}
