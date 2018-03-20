package com.jcr.popularmovies.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.utilities.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private final Context mContext;
    private final MoviesAdapterClickHandler mClickHandler;

    private ArrayList<MovieModel> mMovies;

    public MoviesAdapter(@NonNull Context context, MoviesAdapterClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);

        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        Picasso.with(mContext).load(
                ImageUtils.generatePosterUrl(mMovies.get(position).getPosterPath()))
                .into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    public void addMovies(ArrayList<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public interface MoviesAdapterClickHandler {
        void onClick(MovieModel movie);
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView posterImage;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.movie_poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMovies.get(adapterPosition));
        }
    }
}
