package com.anujdutt.twitterdatacollector.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.databinding.TopicsListRecyclerViewItemBinding;
import com.anujdutt.twitterdatacollector.entity.Topic;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TopicsAdapter extends DataBoundRecyclerViewAdapter<Topic, TopicsListRecyclerViewItemBinding> {

    public TopicsAdapter(DataBindingComponent dataBindingComponent) {
        super(dataBindingComponent);
    }

    @Override
    RecyclerView.ViewHolder createBinding(ViewGroup parent, int viewType) {
        final TopicsListRecyclerViewItemBinding recyclerViewAlertListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.topics_list_recycler_view_item, parent, false, dataBindingComponent);
        return new ObjectViewHolder(recyclerViewAlertListItemBinding);
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
}
