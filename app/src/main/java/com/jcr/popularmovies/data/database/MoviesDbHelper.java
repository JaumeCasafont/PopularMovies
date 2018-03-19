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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local database for movies data.
 */
public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 3;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " (" +

                        MoviesContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +

                        MoviesContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +

                        MoviesContract.MovieEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +

                        MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT, " +

                        MoviesContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +

                        MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +

                        MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +

                        MoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +

                        MoviesContract.MovieEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0, " +

                " UNIQUE (" + MoviesContract.MovieEntry.COLUMN_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}