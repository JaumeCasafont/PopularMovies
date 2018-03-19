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

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.jcr.popularmovies.data.network.models.MovieModel;

import static com.jcr.popularmovies.data.MoviesRepository.MOVIE_DELETE_KEY;
import static com.jcr.popularmovies.data.MoviesRepository.MOVIE_ADD_KEY;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class MoviesSyncIntentService extends IntentService {

    public MoviesSyncIntentService() {
        super("MoviesSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(MOVIE_ADD_KEY)) {
            MovieModel movie = intent.getParcelableExtra(MOVIE_ADD_KEY);
            MoviesSyncTask.addMovie(this, movie);
        }
        if (extras != null && extras.containsKey(MOVIE_DELETE_KEY)) {
            int movieId = intent.getIntExtra(MOVIE_DELETE_KEY, -1);
            MoviesSyncTask.deleteMovie(this, movieId);
        }
    }
}