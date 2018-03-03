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
import android.net.Uri;
import android.util.Log;

import com.jcr.popularmovies.BuildConfig;
import com.jcr.popularmovies.data.network.ResponseModel;
import com.jcr.popularmovies.data.network.TheMovieDBService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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

    public static void getMovies(Callback<ResponseModel> callback, String criteria, String page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_THEMOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDBService.MoviesService service = retrofit.create(TheMovieDBService.MoviesService.class);

        Call<ResponseModel> call = service.getMovies(criteria, BuildConfig.THEMOVIEDB_API_KEY, page);
        call.enqueue(callback);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}