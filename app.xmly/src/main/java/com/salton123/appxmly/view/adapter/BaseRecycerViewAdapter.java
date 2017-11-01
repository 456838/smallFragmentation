package com.salton123.appxmly.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/14 9:51
 * ModifyTime: 9:51
 * Description:
 */
public abstract class BaseRecycerViewAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    protected OnItemClickListener mClickListener;
    public Context mContext;
    public List<T> list = new ArrayList<>();
    public LayoutInflater inflater;

//    public BaseRecycerViewAdapter(Context context, List<T> list) {
//        this.context = context;
//        this.list = new ArrayList<>();
//
//        addAll(list);
//        inflater = LayoutInflater.from(context);
//    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public BaseRecycerViewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return getCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        getBindViewHolder(holder, position);
    }

    public abstract V getCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void getBindViewHolder(V holder, int position);

    public void addAll(Collection<? extends T> collection) {
        addAll(list.size(), collection);
    }

    public void addAll(int position, Collection<? extends T> collection) {
        list.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }

    public List<T> getData() {
        return list;
    }

    public void add(T item){
        list.add(item);
    }

    public void setData(Collection<? extends T> collection) {
        list.clear();
        list.addAll(collection);
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
    }
}

