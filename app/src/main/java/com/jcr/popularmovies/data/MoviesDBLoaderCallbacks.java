package com.jcr.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.ui.OnLoaderFinishedCallback;
import com.jcr.popularmovies.utilities.MoviesDatabaseUtils;

import static com.jcr.popularmovies.data.MoviesRepository.MOVIES_LIST_LOADER_ID;
import static com.jcr.popularmovies.data.MoviesRepository.MOVIES_LIST_PROJECTION;

public class MoviesDBLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private OnLoaderFinishedCallback mCallback;

    public MoviesDBLoaderCallbacks(Context context, OnLoaderFinishedCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case MOVIES_LIST_LOADER_ID:
                Uri moviesQueryUri = MoviesContract.MovieEntry.CONTENT_URI;
                String sortOrder = MoviesContract.getProjectionForSortCriteria(mContext);

                return new CursorLoader(mContext,
                        moviesQueryUri,
                        MOVIES_LIST_PROJECTION,
                        null,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCallback.onLoaderFinished(MoviesDatabaseUtils.getMoviesFromCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
