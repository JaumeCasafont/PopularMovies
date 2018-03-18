package com.jcr.popularmovies.data.network;

import com.jcr.popularmovies.data.network.models.ResponseModel;
import com.jcr.popularmovies.data.network.models.ResponseReviews;
import com.jcr.popularmovies.data.network.models.ResponseVideos;

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

        @GET("{id}/videos")
        Call<ResponseVideos> getVideos(
                @Path("id") String id,
                @Query(API_KEY_PARAM) String apiKey);

        @GET("{id}/reviews")
        Call<ResponseReviews> getReviews(
                @Path("id") String id,
                @Query(API_KEY_PARAM) String apiKey);
    }
}
