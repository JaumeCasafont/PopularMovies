package com.jcr.popularmovies.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.ui.OnLoaderFinishedCallback;

import static com.jcr.popularmovies.ui.list.MainActivity.MOVIES_LIST_LOADER_ID;

public class MoviesGridLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] MOVIES_LIST_PROJECTION = {
            MoviesContract.MovieEntry.COLUMN_ID,
            MoviesContract.MovieEntry.COLUMN_POSTER_PATH
    };

    private Context mContext;
    private OnLoaderFinishedCallback mCallback;

    public MoviesGridLoaderCallbacks(Context context, OnLoaderFinishedCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case MOVIES_LIST_LOADER_ID:
                Uri moviesQueryUri = MoviesContract.MovieEntry.CONTENT_URI;
                String sortOrder = MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " ASC";

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
        mCallback.onLoaderFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
