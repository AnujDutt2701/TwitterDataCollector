package com.anujdutt.twitterdatacollector.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.databinding.SearchResultRecyclerViewItemBinding;
import com.twitter.sdk.android.core.models.Tweet;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultAdapter extends DataBoundRecyclerViewAdapter<Tweet, SearchResultRecyclerViewItemBinding> {

    public SearchResultAdapter(androidx.databinding.DataBindingComponent dataBindingComponent) {
        super(dataBindingComponent);
    }

    @Override
    RecyclerView.ViewHolder createBinding(ViewGroup parent, int viewType) {
        final SearchResultRecyclerViewItemBinding recyclerViewAlertListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_result_recycler_view_item, parent, false, dataBindingComponent);
        return new ObjectViewHolder(recyclerViewAlertListItemBinding);
    }

    @Override
    void bindDataToViewHolder(RecyclerView.ViewHolder holder, Tweet data) {
        ((ObjectViewHolder) holder).viewDataBinding.setTweet(data);
    }

    @Override
    boolean checkItemsSimilarity(Tweet oldItem, Tweet newItem) {
        return oldItem.idStr.equals(newItem.idStr);
    }

    @Override
    boolean checkItemsContentsSimilarity(Tweet oldItem, Tweet newItem) {
        return oldItem.equals(newItem);
    }
}

