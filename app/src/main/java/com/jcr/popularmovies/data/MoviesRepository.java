package com.jcr.popularmovies.data;


import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.TheMovieDBService;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.data.network.models.ResponseModel;
import com.jcr.popularmovies.data.network.models.ResponseReviews;
import com.jcr.popularmovies.data.network.models.ResponseVideos;
import com.jcr.popularmovies.data.network.models.ReviewModel;
import com.jcr.popularmovies.data.network.models.VideoModel;
import com.jcr.popularmovies.ui.OnLoadMoviesFinishedCallback;
import com.jcr.popularmovies.ui.OnLoadReviewsFinishedCallback;
import com.jcr.popularmovies.ui.OnLoadVideosFinishedCallback;
import com.jcr.popularmovies.utilities.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class MoviesRepository {

    public static final String[] MOVIES_LIST_PROJECTION = {
            MoviesContract.MovieEntry.COLUMN_OVERVIEW,
            MoviesContract.MovieEntry.COLUMN_TITLE,
            MoviesContract.MovieEntry.COLUMN_POSTER_PATH,
            MoviesContract.MovieEntry.COLUMN_RELEASE_DATE,
            MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MoviesContract.MovieEntry.COLUMN_POPULARITY,
            MoviesContract.MovieEntry.COLUMN_ID
    };

    public static final int INDEX_COLUMN_OVERVIEW = 0;
    public static final int INDEX_COLUMN_TITLE = 1;
    public static final int INDEX_COLUMN_POSTER_PATH = 2;
    public static final int INDEX_COLUMN_RELEASE_DATE = 3;
    public static final int INDEX_COLUMN_VOTE_AVERAGE = 4;
    public static final int INDEX_COLUMN_POPULARITY = 5;
    public static final int INDEX_COLUMN_ID = 6;

    public static final int MOVIES_LIST_LOADER_ID = 127;

    private static MoviesRepository sInstance;

    private static boolean sInitialized;

    private static TheMovieDBService.MoviesService moviesService;

    public MoviesRepository() {
        if (sInitialized) return;
        sInitialized = true;
        sInstance = this;

        moviesService = NetworkUtils.createMoviesService();
    }

    public void getMovies(FragmentActivity activity, final OnLoadMoviesFinishedCallback onLoadFinished) {
        if (NetworkUtils.isConnected(activity)) {
            getMoviesFromNetwork(activity, onLoadFinished);
        } else {
            getFavoriteMoviesFromDB(activity, onLoadFinished);
        }
    }

    private void getMoviesFromNetwork(FragmentActivity activity, final OnLoadMoviesFinishedCallback onLoadFinished){
        NetworkUtils.getMovies(moviesService, activity, new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                MovieModel[] movies = response.body().getResults();
                onLoadFinished.onMoviesLoaded(movies);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                onLoadFinished.onMoviesLoadedError(t);
            }
        });
    }

    private void getFavoriteMoviesFromDB(FragmentActivity activity, final OnLoadMoviesFinishedCallback onLoadFinished) {
        MoviesDBLoaderCallbacks loaderCallbacks = new MoviesDBLoaderCallbacks(activity, onLoadFinished);
        activity.getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, loaderCallbacks);
    }

    public void getVideos(Context context, int id, final OnLoadVideosFinishedCallback onLoadFinished) {
        if (NetworkUtils.isConnected(context)) {
            NetworkUtils.getVideos(moviesService, id, new Callback<ResponseVideos>() {
                @Override
                public void onResponse(Call<ResponseVideos> call, Response<ResponseVideos> response) {
                    VideoModel[] videos = response.body().getResults();
                    onLoadFinished.onVideosLoaded(videos);
                }

                @Override
                public void onFailure(Call<ResponseVideos> call, Throwable t) {
                    onLoadFinished.onVideosLoadedError();
                }
            });
        } else onLoadFinished.onVideosLoadedError();
    }

    public void getReviews(Context context, int id, final OnLoadReviewsFinishedCallback onLoadFinished) {
        if (NetworkUtils.isConnected(context)) {
            NetworkUtils.getReviews(moviesService, id, new Callback<ResponseReviews>() {
                @Override
                public void onResponse(Call<ResponseReviews> call, Response<ResponseReviews> response) {
                    ReviewModel[] reviews = response.body().getResults();
                    onLoadFinished.onReviewsLoaded(reviews);
                }

                @Override
                public void onFailure(Call<ResponseReviews> call, Throwable t) {
                    onLoadFinished.onReviewsLoadedError();
                }
            });
        } else onLoadFinished.onReviewsLoadedError();
    }
}
