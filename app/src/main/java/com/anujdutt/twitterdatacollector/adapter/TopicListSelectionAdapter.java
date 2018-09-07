package com.anujdutt.twitterdatacollector.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.databinding.SimpleTopicsListRecyclerViewItemBinding;
import com.anujdutt.twitterdatacollector.entity.Topic;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TopicListSelectionAdapter extends DataBoundRecyclerViewAdapter<Topic, SimpleTopicsListRecyclerViewItemBinding> {

    TopicClickCallback topicClickCallback;

    public TopicListSelectionAdapter(DataBindingComponent dataBindingComponent, TopicListSelectionAdapter.TopicClickCallback topicClickCallback) {
        super(dataBindingComponent);
        this.topicClickCallback = topicClickCallback;
    }

    @Override
    RecyclerView.ViewHolder createBinding(ViewGroup parent, int viewType) {
        final SimpleTopicsListRecyclerViewItemBinding simpleTopicsListRecyclerViewItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_topics_list_recycler_view_item, parent, false, dataBindingComponent);
        simpleTopicsListRecyclerViewItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                topicClickCallback.onItemClick(simpleTopicsListRecyclerViewItemBinding.getTopic());
            }
        });
        return new ObjectViewHolder(simpleTopicsListRecyclerViewItemBinding);
    }

    @Override
    void bindDataToViewHolder(RecyclerView.ViewHolder holder, Topic data) {
        ((ObjectViewHolder) holder).viewDataBinding.setTopic(data);
    }

    @Override
    boolean checkItemsSimilarity(Topic oldItem, Topic newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    boolean checkItemsContentsSimilarity(Topic oldItem, Topic newItem) {
        return oldItem.equals(newItem);
    }

    public interface TopicClickCallback {

        void onItemClick(Topic topic);
    }
}