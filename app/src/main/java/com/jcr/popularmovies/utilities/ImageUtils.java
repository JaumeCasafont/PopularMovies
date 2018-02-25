package com.jcr.popularmovies.utilities;


public final class ImageUtils {

    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";

    public static String generatePosterUrl(String poster) {
        return BASE_POSTER_URL + "w185//" + poster;
    }
}
