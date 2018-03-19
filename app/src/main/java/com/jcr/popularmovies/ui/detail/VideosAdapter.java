package com.jcr.popularmovies.ui.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.network.models.VideoModel;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {

    public static final String YOUTUBE_APP_PATH = "vnd.youtube:";
    public static final String YOUTUBE_WEB_PATH = "http://www.youtube.com/watch?v=";

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
        final ImageView shareIcon;

        public VideosAdapterViewHolder(View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.video_name_tv);
            separatorView = itemView.findViewById(R.id.video_item_separator);
            shareIcon = itemView.findViewById(R.id.share_icon);
            itemView.setOnClickListener(this);
            shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareMovieVideoIntent = createShareMovieVideoIntent();
                    mContext.startActivity(shareMovieVideoIntent);
                }
            });
        }

        private Intent createShareMovieVideoIntent() {
            Intent shareIntent = ShareCompat.IntentBuilder.from((Activity) mContext)
                    .setType("text/plain")
                    .setText(YOUTUBE_WEB_PATH + mVideos[getAdapterPosition()].getKey())
                    .getIntent();
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            return shareIntent;
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mVideos[adapterPosition].getKey());
        }
    }
}
