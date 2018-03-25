package com.jcr.popularmovies.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import java.util.Arrays;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private final Context mContext;
    private final MoviesAdapterClickHandler mClickHandler;

    private int dataVersion = 0;

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

    public ArrayList<MovieModel> getData() {
        return mMovies;
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    public void updateMovies(final ArrayList<MovieModel> movies) {
        dataVersion ++;
        if (mMovies == null) {
            if (movies == null) {
                return;
            }
            mMovies = movies;
            notifyDataSetChanged();
        } else if (movies == null) {
            int oldSize = mMovies.size();
            mMovies = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final ArrayList<MovieModel> oldmMovies = mMovies;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                @Override
                protected DiffUtil.DiffResult  doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldmMovies.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return movies.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return oldmMovies.get(oldItemPosition).getId()
                                    == movies.get(newItemPosition).getId();
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            final MovieModel oldMovie = oldmMovies.get(oldItemPosition);
                            final MovieModel newMovie = movies.get(newItemPosition);

                            return oldMovie.getTitle().equals(newMovie.getTitle());
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        // ignore movies
                        return;
                    }
                    mMovies = movies;
                    diffResult.dispatchUpdatesTo(MoviesAdapter.this);

                }
            }.execute();
        }
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
