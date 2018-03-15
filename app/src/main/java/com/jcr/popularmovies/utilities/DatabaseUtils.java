package com.jcr.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.MovieModel;

public final class DatabaseUtils {

    public static ContentValues[] getMoviesContentValues(Context context, MovieModel[] movieModels) {
        ContentValues[] movieContentValues = new ContentValues[movieModels.length];
        for(int i = 0; i < movieModels.length; i++) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MoviesContract.MovieEntry.COLUMN_ID, movieModels[i].getId());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, movieModels[i].getOverview());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_VIDEO, movieModels[i].isVideo());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, movieModels[i].getTitle());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, movieModels[i].getPosterPath());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movieModels[i].getReleaseDate());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieModels[i].getVoteAverage());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, movieModels[i].getPopularity());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_FAVORITE, false);

            movieContentValues[i] = movieValues;
        }
        return movieContentValues;
    }
}
