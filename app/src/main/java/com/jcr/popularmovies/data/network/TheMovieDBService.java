package com.jcr.popularmovies.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TheMovieDBService {

    private final static String API_KEY_PARAM = "api_key";
    private final static String PAGE = "page";

    public interface MoviesService {
        @GET("{criteria}/")
        Call<ResponseModel> getMovies(
                @Path("criteria") String criteria,
                @Query(API_KEY_PARAM) String apiKey,
                @Query(PAGE) String page);
    }
}
