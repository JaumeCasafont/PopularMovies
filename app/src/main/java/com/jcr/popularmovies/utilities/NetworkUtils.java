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
package com.jcr.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jcr.popularmovies.BuildConfig;
import com.jcr.popularmovies.data.PopularMoviesPreferences;
import com.jcr.popularmovies.data.network.models.ResponseModel;
import com.jcr.popularmovies.data.network.TheMovieDBService;
import com.jcr.popularmovies.data.network.models.ResponseReviews;
import com.jcr.popularmovies.data.network.models.ResponseVideos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * These utilities will be used to communicate with the themoviedb servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_THEMOVIEDB_URL = "http://api.themoviedb.org/3/movie/";

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static TheMovieDBService.MoviesService createMoviesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_THEMOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TheMovieDBService.MoviesService.class);
    }

    public static void getMovies(TheMovieDBService.MoviesService service, Context context,
                                 Callback<ResponseModel> callback) {
        String criteria = PopularMoviesPreferences.getSortCriteria(context);
        String page = String.valueOf(PopularMoviesPreferences.getCurrentPage(context));
        Call<ResponseModel> call = service.getMovies(criteria, BuildConfig.THEMOVIEDB_API_KEY, page);
        call.enqueue(callback);
    }

    public static void getVideos(TheMovieDBService.MoviesService service, int id,
                                 Callback<ResponseVideos> callback) {
        Call<ResponseVideos> call = service.getVideos(String.valueOf(id), BuildConfig.THEMOVIEDB_API_KEY);
        call.enqueue(callback);
    }

    public static void getReviews(TheMovieDBService.MoviesService service, int id,
                                 Callback<ResponseReviews> callback) {
        Call<ResponseReviews> call = service.getReviews(String.valueOf(id), BuildConfig.THEMOVIEDB_API_KEY);
        call.enqueue(callback);
    }
}