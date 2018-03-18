package com.jcr.popularmovies;

import android.app.Application;

import com.jcr.popularmovies.data.MoviesRepository;

public class AppPopularMovies extends Application {

    private static MoviesRepository sMoviesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        sMoviesRepository = new MoviesRepository();
    }

    public static MoviesRepository getRepository(){
        return sMoviesRepository;
    }
}
