package com.jcr.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jcr.popularmovies.data.MoviesRepository;
import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.models.MovieModel;

public final class MoviesDatabaseUtils {

   /* public static ContentValues[] getMoviesContentValues(Context context, MovieModel[] movieModels) {
        ContentValues[] movieContentValues = new ContentValues[movieModels.length];
        for(int i = 0; i < movieModels.length; i++) {


            movieContentValues[i] = movieValues;
        }
        return movieContentValues;
    }*/

    public static ContentValues getMovieContentValues(MovieModel movieModel) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MoviesContract.MovieEntry.COLUMN_ID, movieModel.getId());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, movieModel.getOverview());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_VIDEO, movieModel.isVideo());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, movieModel.getTitle());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, movieModel.getPosterPath());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movieModel.getReleaseDate());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieModel.getVoteAverage());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, movieModel.getPopularity());
        movieValues.put(MoviesContract.MovieEntry.COLUMN_FAVORITE, true);

        return movieValues;
    }

    public static MovieModel[] getMoviesFromCursor(Cursor cursor) {
        int length = cursor.getCount();
        MovieModel[] movies = new MovieModel[length];
        cursor.moveToFirst();
        for (int i = 0; i < length; i++) {
            MovieModel movie = new MovieModel(
                    cursor.getString(MoviesRepository.INDEX_COLUMN_OVERVIEW),
                    cursor.getString(MoviesRepository.INDEX_COLUMN_TITLE),
                    cursor.getString(MoviesRepository.INDEX_COLUMN_POSTER_PATH),
                    cursor.getString(MoviesRepository.INDEX_COLUMN_RELEASE_DATE),
                    cursor.getDouble(MoviesRepository.INDEX_COLUMN_VOTE_AVERAGE),
                    cursor.getDouble(MoviesRepository.INDEX_COLUMN_POPULARITY),
                    cursor.getInt(MoviesRepository.INDEX_COLUMN_ID),
                    cursor.getInt(MoviesRepository.INDEX_COLUMN_FAVOURITE)
            );
            movies[i] = movie;
            cursor.moveToNext();
        }
        return movies;
    }

    public static boolean isFavorite(Cursor cursor) {
        return cursor.getCount() > 0;
    }
}
