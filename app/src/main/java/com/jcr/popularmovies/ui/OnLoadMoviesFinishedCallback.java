package com.jcr.popularmovies.ui;

import com.jcr.popularmovies.data.network.models.MovieModel;

public interface OnLoadMoviesFinishedCallback {
    void onLoaderFinished(MovieModel[] movies);
    void onLoaderError(Throwable t);
}
