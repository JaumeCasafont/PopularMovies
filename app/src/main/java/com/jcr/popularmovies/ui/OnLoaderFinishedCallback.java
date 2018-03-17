package com.jcr.popularmovies.ui;

import android.database.Cursor;

import com.jcr.popularmovies.data.network.MovieModel;

public interface OnLoaderFinishedCallback {
    void onLoaderFinished(MovieModel[] movies);
    void onLoaderError(Throwable t);
}
