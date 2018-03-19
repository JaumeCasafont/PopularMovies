/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jcr.popularmovies.data.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.utilities.MoviesDatabaseUtils;

public class MoviesSyncTask {

    synchronized public static void addMovie(final Context context, MovieModel movie) {
        ContentValues movieValues = MoviesDatabaseUtils.getMovieContentValues(movie);
        ContentResolver popularMoviesContentResolver = context.getContentResolver();

        popularMoviesContentResolver.insert(
                MoviesContract.MovieEntry.CONTENT_URI,
                movieValues
        );
    }

    synchronized public static void deleteMovie(final Context context, int id) {
        ContentResolver popularMoviesContentResolver = context.getContentResolver();

        popularMoviesContentResolver.delete(
                MoviesContract.buildMovieUriWithId(id),
                null,
                null
        );
    }
}