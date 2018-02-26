package com.jcr.popularmovies.utilities;

import com.jcr.popularmovies.data.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonParserUtils {

    private static final String MOVIE_RESULTS = "results";

    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER = "poster_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";

    public static MovieModel[] parseJson(final String responseJsonStr) throws JSONException {
        JSONObject responseJson = new JSONObject(responseJsonStr);
        JSONArray moviesJsonArray = responseJson.getJSONArray(MOVIE_RESULTS);
        MovieModel[] movies = new MovieModel[moviesJsonArray.length()];

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieJson = moviesJsonArray.getJSONObject(i);
            MovieModel movie = parseMovieFromJson(movieJson);
            movies[i] = movie;
        }
        return movies;
    }

    private static MovieModel parseMovieFromJson(final JSONObject movieJson) throws JSONException {
        String title = movieJson.getString(TITLE);
        String releaseDate = movieJson.getString(RELEASE_DATE);
        String poster = movieJson.getString(POSTER);
        double voteAverage = movieJson.getDouble(VOTE_AVERAGE);
        String overview = movieJson.getString(OVERVIEW);

        return new MovieModel(title, releaseDate, poster, voteAverage, overview);
    }
}
