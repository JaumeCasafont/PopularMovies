package com.jcr.popularmovies.ui.detail;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.ui.OnLoadFromRepositoryCallback;
import com.jcr.popularmovies.utilities.MoviesDatabaseUtils;

import static com.jcr.popularmovies.data.MoviesRepository.MOVIES_DETAIL_LOADER_ID;
import static com.jcr.popularmovies.data.MoviesRepository.MOVIES_LIST_PROJECTION;

public class MoviesDetailsLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private OnLoadFromRepositoryCallback mCallback;
    private int mMovieId;

    public MoviesDetailsLoaderCallbacks(Context context, OnLoadFromRepositoryCallback callback, int movieId) {
        mContext = context;
        mCallback = callback;
        mMovieId = movieId;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case MOVIES_DETAIL_LOADER_ID:
                Uri moviesDetailQueryUri = MoviesContract.buildMovieUriWithId(mMovieId);
                return new CursorLoader(mContext,
                        moviesDetailQueryUri,
                        MOVIES_LIST_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCallback.onLoad(MoviesDatabaseUtils.isFavorite(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
