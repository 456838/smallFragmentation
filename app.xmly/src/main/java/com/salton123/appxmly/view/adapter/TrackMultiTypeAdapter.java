package com.salton123.appxmly.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.appxmly.R;
import com.salton123.appxmly.view.vh.BaseVH;
import com.salton123.appxmly.view.vh.TrackContentVH;
import com.salton123.appxmly.view.vh.TrackHeaderVH;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 20:45
 * ModifyTime: 20:45
 * Description:
 */
public class TrackMultiTypeAdapter<T> extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_TRACK_HEADER = R.layout.view_type_track_header;
    public static final int VIEW_TYPE_TRACK_CONTENT = R.layout.view_type_track_content;
    public List<T> list = new ArrayList<>();
    protected OnItemClickListener mClickListener;

    public TrackList getTrackList() {
        return trackList;
    }

    public void setTrackList(TrackList trackList) {
        this.trackList = trackList;
        notifyItemChanged(0);
    }

    TrackList trackList;

    public void addAll(Collection<? extends T> collection) {
        addAll(list.size(), collection);
    }

    public void addAll(int position, Collection<? extends T> collection) {
        list.addAll(position, collection);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return list;
    }

    public void add(T item) {
        list.add(item);
    }

    public void setData(Collection<? extends T> collection) {
        list.clear();
        list.addAll(collection);
    }

    public void clear() {
        list.clear();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TRACK_HEADER;
        } else {
            return VIEW_TYPE_TRACK_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(viewType, parent, false);
//        return new TrackContentVH(itemView);
        if (viewType == VIEW_TYPE_TRACK_HEADER) {
            return new TrackHeaderVH(itemView);
        } else {
            return new TrackContentVH(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ((BaseVH) holder).initData(getTrackList());
        } else {
            ((BaseVH) holder).initData(list.get(position - 1));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(position - 1, v, holder);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


}
