package com.jcr.popularmovies.ui;

import com.jcr.popularmovies.data.network.models.MovieModel;

public interface OnLoadMoviesFinishedCallback {
    void onMoviesLoaded(MovieModel[] movies);
    void onMoviesLoadedError(Throwable t);
}
