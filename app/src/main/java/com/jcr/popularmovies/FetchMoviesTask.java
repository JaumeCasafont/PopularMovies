package com.jcr.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.jcr.popularmovies.data.MovieModel;
import com.jcr.popularmovies.utilities.JsonParserUtils;
import com.jcr.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class FetchMoviesTask extends AsyncTask<String, Void, MovieModel[]> {

    private static final String TAG = "FetchMoviesTask";

    private AsyncTaskListener<MovieModel[]> listener;

    public FetchMoviesTask(AsyncTaskListener<MovieModel[]> listener) {
        this.listener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        listener.onTaskStarted();
    }

    @Override
    protected MovieModel[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
        if (params.length == 0) {
            return null;
        }

        String criteria = params[0];
        String page = params[1];
        URL moviesRequestUrl = NetworkUtils.buildUrl(criteria, page);

        try {
            String jsonMoviesResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl);

            return JsonParserUtils.parseJson(jsonMoviesResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(MovieModel[] movies) {
        super.onPostExecute(movies);
        listener.onTaskComplete(movies);
    }
}
