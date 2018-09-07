package com.anujdutt.twitterdatacollector.adapter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class DataBoundRecyclerViewAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    DataBindingComponent dataBindingComponent;
    private List<T> dataList;
    private int dataVersion = 0;

    public DataBoundRecyclerViewAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    public class ObjectViewHolder extends RecyclerView.ViewHolder {

        V viewDataBinding;

        public ObjectViewHolder(V viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createBinding(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindDataToViewHolder(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @SuppressLint("StaticFieldLeak")
    public void setDataList(final List<T> update) {
        dataVersion++;
        if (dataList == null) {
            if (update == null) {
                return;
            }
            dataList = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = dataList.size();
            dataList = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final List<T> oldItems = dataList;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {

                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {

                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return checkItemsSimilarity(oldItems.get(oldItemPosition), update.get(newItemPosition));
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return checkItemsContentsSimilarity(oldItems.get(oldItemPosition), update.get(newItemPosition));
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        return;
                    }
                    dataList = update;
                    diffResult.dispatchUpdatesTo(DataBoundRecyclerViewAdapter.this);
                }
            }.execute();
        }
    }

    abstract RecyclerView.ViewHolder createBinding(ViewGroup parent, int viewType);

    abstract void bindDataToViewHolder(RecyclerView.ViewHolder holder, T data);

    abstract boolean checkItemsSimilarity(T oldItem, T newItem);

    abstract boolean checkItemsContentsSimilarity(T oldItem, T newItem);

}