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
package com.jcr.popularmovies.data.database;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.PopularMoviesPreferences;

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.jcr.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_OVERVIEW = "overview";

        /* Stored as a boolean */
        public static final String COLUMN_VIDEO = "video";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        /* Stored as a double */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /* Stored as a double */
        public static final String COLUMN_POPULARITY = "popularity";

        /* Stored as an int */
        public static final String COLUMN_ID = "id";

        /* Stored as a boolean */
        public static final String COLUMN_FAVORITE = "favorite";
    }

    public static String getProjectionForSortCriteria(Context context) {
        String sortCriteria = PopularMoviesPreferences.getSortCriteria(context);
        String column = sortCriteria.equals(context.getResources().getString(R.string.pref_sort_criteria_popular_value)) ? MovieEntry.COLUMN_POPULARITY :
            MovieEntry.COLUMN_VOTE_AVERAGE;
        return column + " DESC";
    }
}