package com.jcr.popularmovies.data;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.TheMovieDBService;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.data.network.models.ResponseModel;
import com.jcr.popularmovies.data.network.models.ResponseReviews;
import com.jcr.popularmovies.data.network.models.ResponseVideos;
import com.jcr.popularmovies.data.network.models.ReviewModel;
import com.jcr.popularmovies.data.network.models.VideoModel;
import com.jcr.popularmovies.data.sync.MoviesSyncIntentService;
import com.jcr.popularmovies.ui.OnLoadFromRepositoryCallback;
import com.jcr.popularmovies.ui.detail.MoviesDetailsLoaderCallbacks;
import com.jcr.popularmovies.ui.list.MoviesListLoaderCallbacks;
import com.jcr.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;

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
            MoviesContract.MovieEntry.COLUMN_ID,
            MoviesContract.MovieEntry.COLUMN_FAVORITE
    };

    public static final int INDEX_COLUMN_OVERVIEW = 0;
    public static final int INDEX_COLUMN_TITLE = 1;
    public static final int INDEX_COLUMN_POSTER_PATH = 2;
    public static final int INDEX_COLUMN_RELEASE_DATE = 3;
    public static final int INDEX_COLUMN_VOTE_AVERAGE = 4;
    public static final int INDEX_COLUMN_POPULARITY = 5;
    public static final int INDEX_COLUMN_ID = 6;
    public static final int INDEX_COLUMN_FAVOURITE = 7;

    public static final int MOVIES_LIST_LOADER_ID = 127;
    public static final int MOVIES_DETAIL_LOADER_ID = 721;

    public static final String MOVIE_ADD_KEY = "movie_add";
    public static final String MOVIE_DELETE_KEY = "movie_delete";

    private static boolean sInitialized;

    private static TheMovieDBService.MoviesService moviesService;

    public MoviesRepository() {
        if (sInitialized) return;
        sInitialized = true;

        moviesService = NetworkUtils.createMoviesService();
    }

    public void getMovies(FragmentActivity activity, final OnLoadFromRepositoryCallback onLoadFinished) {
        if (NetworkUtils.isConnected(activity) && !PopularMoviesPreferences.isDisplayingFavorites(activity)) {
            getMoviesFromNetwork(activity, onLoadFinished);
        } else {
            getFavoriteMoviesFromDB(activity, onLoadFinished);
        }
    }

    private void getMoviesFromNetwork(FragmentActivity activity, final OnLoadFromRepositoryCallback onLoadFinished){
        NetworkUtils.getMovies(moviesService, activity, new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ArrayList<MovieModel> movies = response.body().getResults();
                onLoadFinished.onLoad(movies);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                onLoadFinished.onError();
            }
        });
    }

    public void getFavoriteMoviesFromDB(FragmentActivity activity, final OnLoadFromRepositoryCallback onLoadFinished) {
        MoviesListLoaderCallbacks loaderCallbacks = new MoviesListLoaderCallbacks(activity, onLoadFinished);
        activity.getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, loaderCallbacks);
    }

    public void getVideos(Context context, int id, final OnLoadFromRepositoryCallback onLoadFinished) {
        if (NetworkUtils.isConnected(context)) {
            NetworkUtils.getVideos(moviesService, id, new Callback<ResponseVideos>() {
                @Override
                public void onResponse(Call<ResponseVideos> call, Response<ResponseVideos> response) {
                    VideoModel[] videos = response.body().getResults();
                    onLoadFinished.onLoad(videos);
                }

                @Override
                public void onFailure(Call<ResponseVideos> call, Throwable t) {
                    onLoadFinished.onError();
                }
            });
        } else onLoadFinished.onError();
    }

    public void getReviews(Context context, int id, final OnLoadFromRepositoryCallback onLoadFinished) {
        if (NetworkUtils.isConnected(context)) {
            NetworkUtils.getReviews(moviesService, id, new Callback<ResponseReviews>() {
                @Override
                public void onResponse(Call<ResponseReviews> call, Response<ResponseReviews> response) {
                    ReviewModel[] reviews = response.body().getResults();
                    onLoadFinished.onLoad(reviews);
                }

                @Override
                public void onFailure(Call<ResponseReviews> call, Throwable t) {
                    onLoadFinished.onError();
                }
            });
        } else onLoadFinished.onError();
    }

    public void saveMovie(Context context, MovieModel movie) {
        Intent intentToSyncImmediately = new Intent(context, MoviesSyncIntentService.class);
        intentToSyncImmediately.putExtra(MOVIE_ADD_KEY, movie);
        context.startService(intentToSyncImmediately);
    }

    public void deleteMovie(Context context, int movieId) {
        Intent intentToSyncImmediately = new Intent(context, MoviesSyncIntentService.class);
        intentToSyncImmediately.putExtra(MOVIE_DELETE_KEY, movieId);
        context.startService(intentToSyncImmediately);
    }

    public void isFavorite(FragmentActivity activity, int movieId, final OnLoadFromRepositoryCallback onLoadFinished) {
        MoviesDetailsLoaderCallbacks loaderCallbacks = new MoviesDetailsLoaderCallbacks(activity, onLoadFinished, movieId);
        activity.getSupportLoaderManager().initLoader(MOVIES_DETAIL_LOADER_ID, null, loaderCallbacks);
    }
}
