package com.jcr.popularmovies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.network.models.VideoModel;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {

    private final Context mContext;
    private final VideosAdapterClickHandler mClickHandler;

    private VideoModel[] mVideos;

    public VideosAdapter(@NonNull Context context, VideosAdapterClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public VideosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item, parent, false);
        view.setFocusable(true);

        return new VideosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosAdapterViewHolder holder, int position) {
        holder.videoName.setText(mVideos[position].getName());
        holder.separatorView.setVisibility(position == mVideos.length-1? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (null == mVideos) return 0;
        return mVideos.length;
    }

    public void addVideos(VideoModel[] videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public interface VideosAdapterClickHandler {
        void onClick(String videoKey);
    }

    class VideosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView videoName;
        final View separatorView;

        public VideosAdapterViewHolder(View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.video_name_tv);
            separatorView = itemView.findViewById(R.id.video_item_separator);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mVideos[adapterPosition].getKey());
        }
    }
}
