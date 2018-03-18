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

import android.content.Context;

public class MoviesSyncTask {

    /**
     * Performs the network request to get movies and inserts the new weather information into our
     * ContentProvider.
     * @param context Used to access utility methods and the ContentResolver
     */
    synchronized public static void syncMovies(final Context context) {
//        if (NetworkUtils.isConnected(context)) {
//            NetworkUtils.getMovies(context, new Callback<ResponseModel>() {
//                @Override
//                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                    MovieModel[] movies = response.body().getResults();
//                    ContentValues[] movieValues = MoviesDatabaseUtils.getMoviesContentValues(context, movies);
//
//                    if (movieValues != null && movieValues.length != 0) {
//                        ContentResolver popularMoviesContentResolver = context.getContentResolver();
//
//                        popularMoviesContentResolver.bulkInsert(
//                                MoviesContract.MovieEntry.CONTENT_URI,
//                                movieValues
//                        );
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseModel> call, Throwable t) {
//
//                }
//            });
//
//        }
    }
}